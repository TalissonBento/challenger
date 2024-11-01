package com.talissonb.challenger.core.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Semaphore

private const val CHANNEL_DEFAULT_CAPACITY = 64

@Suppress("LoopWithTooManyJumpStatements", "TooGenericExceptionCaught")
internal fun <T, R> Flow<T>.flatMapConcurrently(
    maxConcurrency: Int = DEFAULT_CONCURRENCY,
    buffer: Int = CHANNEL_DEFAULT_CAPACITY,
    transform: suspend (T) -> Flow<R>,
): Flow<R> {
    require(maxConcurrency > 0) { "Expected maxConcurrency to be > 0 but was $maxConcurrency" }
    require(buffer > 1) { "Expected buffer to be > 1 but was $buffer" }

    return flow {
        val semaphore = Semaphore(permits = maxConcurrency, acquiredPermits = 1)

        supervisorScope {
            val channel = Channel<T>(0)
            val upstreamJob = launch {
                val upstreamCollectExceptionOrNull = runCatching {
                    collect {
                        channel.send(it)
                        semaphore.acquire()
                    }
                }.exceptionOrNull()
                channel.close(upstreamCollectExceptionOrNull)
            }

            var exceptionWasThrownEarlier = CompletableDeferred<Nothing>()
            while (true) {
                val dataResult = try {
                    select<ChannelResult<T>> {
                        channel.onReceiveCatching { it }
                        exceptionWasThrownEarlier.onAwait { it }
                    }
                } catch (thrown: Throwable) {
                    upstreamJob.cancel(thrown.asCancellation())
                    break
                }
                if (dataResult.isClosed) {
                    val ex = dataResult.exceptionOrNull()
                    if (ex != null) {
                        emit(async { throw ex })
                    }
                    break
                }
                val data = dataResult.getOrThrow()
                val exceptionWasThrownEarlierOrHere = CompletableDeferred<Nothing>()

                val evalTransform = async { transform(data) }
                evalTransform.invokeOnCompletion { thrown ->
                    if (thrown != null) {
                        exceptionWasThrownEarlierOrHere.completeExceptionally(thrown)
                    } else {
                        semaphore.release()
                    }
                }
                exceptionWasThrownEarlier.invokeOnCompletion { thrown ->
                    evalTransform.cancel(thrown!!.asCancellation())
                    exceptionWasThrownEarlierOrHere.completeExceptionally(thrown)
                }
                emit(evalTransform)
                exceptionWasThrownEarlier = exceptionWasThrownEarlierOrHere
            }
        }
    }
        .buffer(if (buffer == Int.MAX_VALUE) buffer else buffer - 2)
        .flatMapConcat { it.await() }
}

private fun Throwable.asCancellation(): CancellationException =
    this as? CancellationException ?: CancellationException(null, this)

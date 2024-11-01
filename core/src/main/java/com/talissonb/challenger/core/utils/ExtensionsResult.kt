package com.talissonb.challenger.core.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException

inline fun <R> resultOf(block: () -> R): Result<R> = try {
    Result.success(block())
} catch (t: TimeoutCancellationException) {
    Result.failure(t)
} catch (c: CancellationException) {
    throw c
} catch (e: Exception) {
    Result.failure(e)
}

package com.talissonb.challenger.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
inline fun <reified T> Flow<T>.collectWithLifecycle(
    key: Any = Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit,
) {
    val lifecycleAwareFlow = remember(this, lifecycleOwner) {
        flowWithLifecycle(
            lifecycle = lifecycleOwner.lifecycle,
            minActiveState = minActiveState,
        )
    }

    LaunchedEffect(key, action) {
        lifecycleAwareFlow.collect { action(it) }
    }
}

package com.talissonb.challenger.basicfeature.presentation.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RocketsLoadingPlaceholder(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxSize(),
    )
}

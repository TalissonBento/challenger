package com.talissonb.challenger.basicfeature.presentation.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.talissonb.challenger.basicfeature.R
import com.talissonb.challenger.basicfeature.presentation.RocketsEvent
import com.talissonb.challenger.basicfeature.presentation.RocketsEvent.OpenWebBrowserWithDetails
import com.talissonb.challenger.basicfeature.presentation.RocketsIntent
import com.talissonb.challenger.basicfeature.presentation.RocketsIntent.RefreshRockets
import com.talissonb.challenger.basicfeature.presentation.RocketsIntent.RocketClicked
import com.talissonb.challenger.basicfeature.presentation.RocketsUiState
import com.talissonb.challenger.basicfeature.presentation.RocketsViewModel
import com.talissonb.challenger.core.utils.collectWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun RocketsRoute(viewModel: RocketsViewModel = hiltViewModel()) {
    HandleEvents(viewModel.getEvents())
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RocketsScreen(
        uiState = uiState,
        onIntent = viewModel::acceptIntent,
    )
}

@Composable
internal fun RocketsScreen(
    uiState: RocketsUiState,
    onIntent: (RocketsIntent) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        PullToRefreshBox(
            modifier = Modifier.padding(paddingValues),
            isRefreshing = uiState.isLoading,
            onRefresh = { onIntent(RefreshRockets) },
        ) {
            if (uiState.rockets.isNotEmpty()) {
                RocketsAvailableContent(
                    snackbarHostState = snackbarHostState,
                    uiState = uiState,
                    onRocketClick = { onIntent(RocketClicked(it)) },
                )
            } else {
                RocketsNotAvailableContent(
                    uiState = uiState,
                )
            }
        }
    }
}

@Composable
private fun HandleEvents(events: Flow<RocketsEvent>) {
    val uriHandler = LocalUriHandler.current

    events.collectWithLifecycle {
        when (it) {
            is OpenWebBrowserWithDetails -> {
                uriHandler.openUri(it.uri)
            }
        }
    }
}

@Composable
private fun RocketsAvailableContent(
    snackbarHostState: SnackbarHostState,
    uiState: RocketsUiState,
    onRocketClick: (String) -> Unit,
) {
    if (uiState.isError) {
        val errorMessage = stringResource(R.string.rockets_error_refreshing)

        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = errorMessage,
            )
        }
    }

    RocketsListContent(
        rocketList = uiState.rockets,
        onRocketClick = onRocketClick,
    )
}

@Composable
private fun RocketsNotAvailableContent(uiState: RocketsUiState) {
    when {
        uiState.isLoading -> RocketsLoadingPlaceholder()
        uiState.isError -> RocketsErrorContent()
    }
}

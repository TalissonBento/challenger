package com.talissonb.challenger.basicfeature.presentation

sealed class RocketsEvent {
    data class OpenWebBrowserWithDetails(val uri: String) : RocketsEvent()
}

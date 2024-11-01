package com.talissonb.challenger.basicfeature.presentation

sealed class RocketsIntent {
    data object RefreshRockets : RocketsIntent()

    data class RocketClicked(val uri: String) : RocketsIntent()
}

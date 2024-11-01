package com.talissonb.challenger.core.navigation

import kotlinx.serialization.Serializable

sealed class NavigationDestination {
    @Serializable
    data object Rockets : NavigationDestination()

    @Serializable
    data object Back : NavigationDestination()
}

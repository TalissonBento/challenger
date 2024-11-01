package com.talissonb.challenger.basicfeature.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.talissonb.challenger.basicfeature.presentation.composable.RocketsRoute
import com.talissonb.challenger.core.navigation.NavigationDestination.Rockets
import com.talissonb.challenger.core.navigation.NavigationFactory
import javax.inject.Inject

class RocketsNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable<Rockets> {
            RocketsRoute()
        }
    }
}

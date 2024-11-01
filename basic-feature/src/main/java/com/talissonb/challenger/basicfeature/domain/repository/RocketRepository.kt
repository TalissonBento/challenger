package com.talissonb.challenger.basicfeature.domain.repository

import com.talissonb.challenger.basicfeature.domain.model.Rocket
import kotlinx.coroutines.flow.Flow

interface RocketRepository {
    fun getRockets(): Flow<List<Rocket>>

    suspend fun refreshRockets()
}

package com.talissonb.challenger.basicfeature.data.repository

import com.talissonb.challenger.basicfeature.data.local.dao.RocketDao
import com.talissonb.challenger.basicfeature.data.mapper.toDomainModel
import com.talissonb.challenger.basicfeature.data.mapper.toEntityModel
import com.talissonb.challenger.basicfeature.data.remote.api.RocketApi
import com.talissonb.challenger.basicfeature.domain.model.Rocket
import com.talissonb.challenger.basicfeature.domain.repository.RocketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RocketRepositoryImpl @Inject constructor(
    private val rocketApi: RocketApi,
    private val rocketDao: RocketDao,
) : RocketRepository {

    override fun getRockets(): Flow<List<Rocket>> = rocketDao.getRockets()
        .map { rocketsCached ->
            rocketsCached.map { it.toDomainModel() }
        }
        .onEach { rockets ->
            if (rockets.isEmpty()) {
                refreshRockets()
            }
        }

    override suspend fun refreshRockets() {
        rocketApi
            .getRockets()
            .map {
                it.toDomainModel().toEntityModel()
            }
            .also {
                rocketDao.saveRockets(it)
            }
    }
}

package com.talissonb.challenger.basicfeature.domain.usecase

import com.talissonb.challenger.basicfeature.domain.repository.RocketRepository
import com.talissonb.challenger.core.utils.resultOf

fun interface RefreshRocketsUseCase : suspend () -> Result<Unit>

suspend fun refreshRockets(rocketRepository: RocketRepository): Result<Unit> = resultOf {
    rocketRepository.refreshRockets()
}

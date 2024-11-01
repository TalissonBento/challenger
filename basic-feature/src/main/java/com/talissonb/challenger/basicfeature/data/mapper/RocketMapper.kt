package com.talissonb.challenger.basicfeature.data.mapper

import com.talissonb.challenger.basicfeature.data.local.model.RocketCached
import com.talissonb.challenger.basicfeature.data.remote.model.RocketResponse
import com.talissonb.challenger.basicfeature.domain.model.Rocket
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun RocketResponse.toDomainModel() = Rocket(
    id = id,
    name = name,
    costPerLaunch = costPerLaunch,
    firstFlight = LocalDate.parse(firstFlightDate),
    height = height.meters.toInt(),
    weight = weight.kg,
    wikiUrl = wikiUrl,
    imageUrl = imageUrls.random(),
)

fun RocketCached.toDomainModel() = Rocket(
    id = id,
    name = name,
    costPerLaunch = costPerLaunch,
    firstFlight = LocalDate.parse(firstFlightDate),
    height = height,
    weight = weight,
    wikiUrl = wikiUrl,
    imageUrl = imageUrl,
)

fun Rocket.toEntityModel() = RocketCached(
    id = id,
    name = name,
    costPerLaunch = costPerLaunch,
    firstFlightDate = firstFlight.format(DateTimeFormatter.ISO_LOCAL_DATE),
    height = height,
    weight = weight,
    wikiUrl = wikiUrl,
    imageUrl = imageUrl,
)

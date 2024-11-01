package com.talissonb.challenger.basicfeature.data.remote.api

import com.talissonb.challenger.basicfeature.data.remote.model.RocketResponse
import retrofit2.http.GET

interface RocketApi {

    @GET("rockets")
    suspend fun getRockets(): List<RocketResponse>
}

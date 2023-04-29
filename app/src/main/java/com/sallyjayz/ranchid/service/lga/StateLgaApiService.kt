package com.sallyjayz.ranchid.service.lga

import com.sallyjayz.ranchid.model.lga.LGAResponse
import com.sallyjayz.ranchid.model.state.StateResponse
import retrofit2.Response
import retrofit2.http.GET

interface StateLgaApiService {
    @GET("/api/state_locals")
    suspend fun getAllLGA(): Response<LGAResponse>

    /*@GET("api/state_locals")
    suspend fun getAllLGA(): Response<Data>*/

    @GET("/api/state_locals")
    suspend fun getAllState(): Response<StateResponse>
}
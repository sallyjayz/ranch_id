package com.sallyjayz.ranchid.service.farmlocation

import com.sallyjayz.ranchid.model.farmlocation.FarmLocationResponse
import retrofit2.Response
import retrofit2.http.GET

interface FarmLocationApiService {

    @GET("/api/locations")
    suspend fun getAllFarmLocation(): Response<FarmLocationResponse>

}
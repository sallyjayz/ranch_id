package com.sallyjayz.ranchid.service.farmlocation

import com.sallyjayz.ranchid.model.farmlocation.FarmLocationWithStateResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Salama Jatau on 16-Jan-25.
 */
interface FarmLocationWithStateApiService {

    @GET("/api/enumerator/registration_location")
    suspend fun getAllFarmLocationWithState(): Response<FarmLocationWithStateResponse>

}
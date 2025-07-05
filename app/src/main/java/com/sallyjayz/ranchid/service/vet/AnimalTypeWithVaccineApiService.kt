package com.sallyjayz.ranchid.service.vet

import com.sallyjayz.ranchid.model.vet.livestocktypewithvaccine.LivestockTypeWithVaccineResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Salama Jatau on 29-Jun-24.
 */
interface AnimalTypeWithVaccineApiService {

//    /api/vet/get_vaccines

    @GET("/api/vet/get_vaccines")
    suspend fun getAllAnimalTypeWithVaccine(): Response<LivestockTypeWithVaccineResponse>

}
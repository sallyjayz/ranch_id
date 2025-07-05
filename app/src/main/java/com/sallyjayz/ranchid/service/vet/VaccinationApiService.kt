package com.sallyjayz.ranchid.service.vet

import com.sallyjayz.ranchid.model.vet.vaccination.Vaccination
import com.sallyjayz.ranchid.model.vet.vaccination.VaccinationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by Salama Jatau on 22-Mar-24.
 */
interface VaccinationApiService {

    // /api/vet/add_vaccination

    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @POST("/api/vet/add_vaccination")
    suspend fun addVaccination(
        @Body vaccination: Vaccination
    ): Response<VaccinationResponse>

}
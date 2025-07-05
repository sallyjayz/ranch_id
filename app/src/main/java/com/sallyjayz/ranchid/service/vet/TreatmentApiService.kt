package com.sallyjayz.ranchid.service.vet

import com.sallyjayz.ranchid.model.vet.treatment.Treatment
import com.sallyjayz.ranchid.model.vet.treatment.TreatmentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by Salama Jatau on 27-Jun-24.
 */
interface TreatmentApiService {
    //    /api/vet/add_treatment

    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @POST("/api/vet/add_treatment")
    suspend fun addTreatment(
        @Body treatment: Treatment
    ): Response<TreatmentResponse>
}
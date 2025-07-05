package com.sallyjayz.ranchid.service.vet

import com.sallyjayz.ranchid.model.vet.treatmenttypes.TreatmentTypeResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Salama Jatau on 05-Jul-24.
 */
interface TreatmentTypeApiService {

//    /api/vet/get_treatment_types

    @GET("/api/vet/get_treatment_types")
    suspend fun getAllTreatmentType(): Response<TreatmentTypeResponse>

}
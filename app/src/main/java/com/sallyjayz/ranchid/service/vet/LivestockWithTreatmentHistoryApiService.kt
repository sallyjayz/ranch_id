package com.sallyjayz.ranchid.service.vet

import com.sallyjayz.ranchid.model.vet.livestockwithtreatmenthistory.LivestockWithTreatmentHistoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Salama Jatau on 27-Jun-24.
 */
interface LivestockWithTreatmentHistoryApiService {

    //         /api/vet/treatments/NGKW00001RN0009

    @GET("/api/vet/treatments/{tagId}")
    suspend fun getLivestockWithTreatmentHistory(
        @Path("tagId") tagId: String
    ): Response<LivestockWithTreatmentHistoryResponse>

}
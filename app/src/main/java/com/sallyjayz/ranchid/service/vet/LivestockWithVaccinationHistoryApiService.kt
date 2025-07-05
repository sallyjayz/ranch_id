package com.sallyjayz.ranchid.service.vet

import com.sallyjayz.ranchid.model.vet.livestockwithvaccinationhistory.LivestockWithVaccinationHistoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Salama Jatau on 22-Mar-24.
 */
interface LivestockWithVaccinationHistoryApiService {

//    /api/vet/get_livestock/NGKW00001RN0009 - old

//    /api/vet/vaccinations/NGKW00001RN0009

    @GET("/api/vet/vaccinations/{tagId}")
    suspend fun getLivestockWithVaccinationHistory(
        @Path("tagId") tagId: String
    ): Response<LivestockWithVaccinationHistoryResponse>

}
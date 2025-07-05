package com.sallyjayz.ranchid.service.vet

import com.sallyjayz.ranchid.model.vet.vetdashboard.VetActivitiesResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Salama Jatau on 19-Mar-24.
 */
interface VetDashboardActivitiesApiService {
    @GET("/api/vet/stats")
    suspend fun getAllVetActivities(): Response<VetActivitiesResponse>
}
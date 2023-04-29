package com.sallyjayz.ranchid.service.dashboard

import com.sallyjayz.ranchid.model.dashboard.ActivitiesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardActivitiesApiService {
//    api/dashboard/stats/BUKKY
    @GET("/api/dashboard/stats/{username}")
    suspend fun getAllActivities(
        @Path("username") username: String
    ): Response<ActivitiesResponse>

}
package com.sallyjayz.ranchid.service.report

import com.sallyjayz.ranchid.model.report.keeper.KeeperListResponse
import com.sallyjayz.ranchid.model.report.location.LocationListResponse
import com.sallyjayz.ranchid.model.report.owner.OwnerListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Salama Jatau on 05-Aug-23.
 */
interface ReportListApiService {

    @GET("/api/enumerator/get_livestock_owners")
    suspend fun getOwnersList(
        @Query("page") page: Int,
        @Query("limit") perPage: Int
    ): OwnerListResponse

    @GET("/api/enumerator/get_livestock_keepers")
    suspend fun getKeepersList(
        @Query("page") page: Int,
        @Query("limit") perPage: Int
    ): KeeperListResponse

    @GET("/api/enumerator/{username}/get_registration_locations")
    suspend fun getLocationsList(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("limit") perPage: Int
    ): LocationListResponse

}
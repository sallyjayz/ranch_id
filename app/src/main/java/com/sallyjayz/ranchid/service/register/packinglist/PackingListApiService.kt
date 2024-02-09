package com.sallyjayz.ranchid.service.register.packinglist

import com.sallyjayz.ranchid.model.register.packinglist.packinglist.PackingListResponse
import com.sallyjayz.ranchid.model.register.packinglist.postpackinglist.PackingListPost
import com.sallyjayz.ranchid.model.register.packinglist.postpackinglist.PackingListPostResponse
import com.sallyjayz.ranchid.model.register.packinglist.scannedlivestock.ScanLivestockResponse
import retrofit2.Response
import retrofit2.http.*


/**
 * Created by Salama Jatau on 18-Jun-23.
 */
interface PackingListApiService {

    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @POST("/api/parking_list/{username}")
    suspend fun packingListPost(
        @Path("username") username: String,
        @Body packingListPost: PackingListPost
    ): Response<PackingListPostResponse>

    @GET("/api/enumerator/get_livestock/{tag_id}")
    suspend fun getPackingListScannedTagDetail(
        @Path("tag_id") tag_id: String
    ): Response<ScanLivestockResponse>

    @GET("/api/parking_list/{username}")
    suspend fun getPackingList(
        @Path("username") username: String
    ): Response<PackingListResponse>

}
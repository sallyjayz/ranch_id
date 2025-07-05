package com.sallyjayz.ranchid.service.register.taglivestock

import com.sallyjayz.ranchid.model.allkeepers.AllKeepersResponse
import com.sallyjayz.ranchid.model.allowners.AllOwnersResponse
import com.sallyjayz.ranchid.model.register.taglivestock.TagLivestock
import com.sallyjayz.ranchid.model.register.taglivestock.TagLivestockResponse
import com.sallyjayz.ranchid.model.report.keeper.KeeperListResponse
import retrofit2.Response
import retrofit2.http.*


interface TagLivestockApiService{

    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @POST("/api/enumerator/tag_livestock")
    suspend fun tagLivestock(
        @Body tagLivestock: TagLivestock
    ): Response<TagLivestockResponse>

    @GET("/api/enumerator/get_livestock_owners?limit=1000000")
    suspend fun getAllOwnersList(): Response<AllOwnersResponse>

    @GET("/api/enumerator/get_livestock_keepers")
    suspend fun getAllKeepersList(): Response<AllKeepersResponse>
}
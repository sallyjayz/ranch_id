package com.sallyjayz.ranchid.service.register.keeper

import com.sallyjayz.ranchid.model.register.keeper.AddKeeperResponse
import com.sallyjayz.ranchid.model.register.keeper.Keeper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface KeeperApiService{
    @Headers(
        "Accept: application/json",
        "Content-type: application/json",
        "CONNECT_TIMEOUT:60000",
        "READ_TIMEOUT:60000",
        "WRITE_TIMEOUT:60000"
    )
    @POST("/api/enumerator/add_livestock_keeper/")
    suspend fun addKeeper(
        @Body keeper: Keeper
    ): Response<AddKeeperResponse>
}

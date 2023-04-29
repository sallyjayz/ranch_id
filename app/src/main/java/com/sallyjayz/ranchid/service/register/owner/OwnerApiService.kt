package com.sallyjayz.ranchid.service.register.owner

import com.sallyjayz.ranchid.model.register.owner.AddOwnerResponse
import com.sallyjayz.ranchid.model.register.owner.Owner
import retrofit2.Response
import retrofit2.http.*

interface OwnerApiService {

    @Headers(
        "Accept: application/json",
        "Content-type: application/json",
        "CONNECT_TIMEOUT:60000",
        "READ_TIMEOUT:60000",
        "WRITE_TIMEOUT:60000")
    @POST("/api/enumerator/add_livestock_owner/")
    suspend fun addOwner(
        @Body owner: Owner
    ): Response<AddOwnerResponse>

}
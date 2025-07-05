package com.sallyjayz.ranchid.service.register.usedenumeratortag

import com.sallyjayz.ranchid.model.usedenumeratortag.UsedEnumeratorTagResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Salama Jatau on 20-Jun-23.
 */
interface UsedEnumeratorTagApiService {

    @GET("/api/enumerator/{username}/used_tags?limit=10000000")
    suspend fun getAllUsedEnumeratorTag(
        @Path("username") username: String
    ): Response<UsedEnumeratorTagResponse>

}
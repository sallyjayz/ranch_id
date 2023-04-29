package com.sallyjayz.ranchid.service.register.unusedenumeratortag

import com.sallyjayz.ranchid.model.unusedenumeratortag.UnusedEnumeratorTagResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UnusedEnumeratorTagApiService {

    @GET("/api/enumerator/{username}/tag/{tag_id}")
    suspend fun getUnusedEnumeratorTag(
        @Path("username") username: String,
        @Path("tag_id") tag_id: String
    ): Response<UnusedEnumeratorTagResponse>

}
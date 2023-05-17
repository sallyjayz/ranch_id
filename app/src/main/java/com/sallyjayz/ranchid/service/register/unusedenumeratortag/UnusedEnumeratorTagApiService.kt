package com.sallyjayz.ranchid.service.register.unusedenumeratortag

import com.sallyjayz.ranchid.model.unusedenumeratortag.all.AllUnusedEnumeratorTagResponse
import com.sallyjayz.ranchid.model.unusedenumeratortag.byId.UnusedEnumeratorTagResponseById
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UnusedEnumeratorTagApiService {

    @GET("/api/enumerator/{username}/tag/{tag_id}")
    suspend fun getUnusedEnumeratorTagById(
        @Path("username") username: String,
        @Path("tag_id") tag_id: String
    ): Response<UnusedEnumeratorTagResponseById>

    @Headers("CONNECT_TIMEOUT:120000", "READ_TIMEOUT:120000", "WRITE_TIMEOUT:120000")
    @GET("/api/enumerator/{username}/unused_tags?limit=3000")
    suspend fun getAllUnusedEnumeratorTag(
        @Path("username") username: String
    ): Response<AllUnusedEnumeratorTagResponse>

}
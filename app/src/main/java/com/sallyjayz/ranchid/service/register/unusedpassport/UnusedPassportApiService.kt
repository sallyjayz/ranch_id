package com.sallyjayz.ranchid.service.register.unusedpassport

import com.sallyjayz.ranchid.model.unusedpassport.UnusedPassportResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UnusedPassportApiService {

    @Headers("CONNECT_TIMEOUT:60000", "READ_TIMEOUT:60000", "WRITE_TIMEOUT:60000")
    @GET("/api/enumerator/{username}/unused_passports")
    suspend fun getUnusedPassport(
        @Path("username") username: String
    ): Response<UnusedPassportResponse>

}
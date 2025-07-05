package com.sallyjayz.ranchid.service.auth

import com.sallyjayz.ranchid.model.auth.Auth
import com.sallyjayz.ranchid.model.auth.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST("/api/auth")
    suspend fun login(
        @Body auth: Auth
    ): Response<LoginResponse>

    @GET("/api/auth")
    suspend fun refreshToken(
        @Header("Authorization") token: String
    ): Response<LoginResponse>


}
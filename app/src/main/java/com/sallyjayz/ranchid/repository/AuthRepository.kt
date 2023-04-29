package com.sallyjayz.ranchid.repository

import com.sallyjayz.ranchid.model.auth.Auth
import com.sallyjayz.ranchid.service.auth.AuthApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService
) {

    fun login(auth: Auth) = apiRequestFlow {
        authApiService.login(auth)
    }
}
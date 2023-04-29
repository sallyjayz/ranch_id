package com.sallyjayz.ranchid.model.auth

data class LoginResponse(
    val user: User,
    val token: String
)

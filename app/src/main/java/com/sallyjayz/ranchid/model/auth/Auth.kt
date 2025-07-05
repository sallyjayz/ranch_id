package com.sallyjayz.ranchid.model.auth

import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("useremail")
    val username: String,
    val password: String,
    val type: String
)

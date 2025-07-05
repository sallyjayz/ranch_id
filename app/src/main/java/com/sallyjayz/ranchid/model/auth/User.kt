package com.sallyjayz.ranchid.model.auth

import com.google.gson.annotations.SerializedName

data class User(
    val username: String,
    @SerializedName("email_address")
    val userEmail: String,
    val surname: String,
    @SerializedName("other_names")
    val otherName: String,
    /*@SerializedName("passport_photo")
    val photo: String,*/
    @SerializedName("user_profile")
    val role: String,
    @SerializedName("vet_council_number")
    val vetCouncilNumber: String?
)

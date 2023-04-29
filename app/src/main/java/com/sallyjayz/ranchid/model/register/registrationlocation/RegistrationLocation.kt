package com.sallyjayz.ranchid.model.register.registrationlocation

import com.google.gson.annotations.SerializedName

data class RegistrationLocation(
    @SerializedName("location_name")
    val locationName: String,
    @SerializedName("location_address")
    val locationAddress: String,
    val state: Int,
    val lga: Int,
    val ward: String,
    @SerializedName("cac_certificate")
    val cacCertificate: Int,
    val longitude: Double?,
    val latitude: Double?,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("owner_name")
    val ownerName: String,
    val timestamp: String
)

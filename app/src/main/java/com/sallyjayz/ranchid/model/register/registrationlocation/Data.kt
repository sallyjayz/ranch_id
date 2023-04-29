package com.sallyjayz.ranchid.model.register.registrationlocation

data class Data(
    val location_name: String,
    val location_address: String,
    val state: Int,
    val lga: Int,
    val ward: String,
    val cac_certificate: Int,
    val longitude: Float,
    val latitude: Float,
    val location_type: String,
    val timestamp: String,
    val owner_name: String,
    val updated_at: String,
    val created_at: String,
    val id: Int
)

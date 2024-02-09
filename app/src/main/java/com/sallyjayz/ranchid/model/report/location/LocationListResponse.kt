package com.sallyjayz.ranchid.model.report.location

import com.google.gson.annotations.SerializedName

data class LocationListResponse(
    @SerializedName("data")
    val locations: List<Location>,
    val status: String
)
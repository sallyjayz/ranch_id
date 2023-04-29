package com.sallyjayz.ranchid.model.farmlocation

import com.google.gson.annotations.SerializedName

data class FarmLocationResponse(
    @SerializedName("data")
    val farmLocation: List<FarmLocation>,
//    val status: String
)

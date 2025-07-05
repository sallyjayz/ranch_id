package com.sallyjayz.ranchid.model.farmlocation

import com.google.gson.annotations.SerializedName

data class FarmLocationWithStateResponse(
    @SerializedName("data")
    val farmLocationWithState: List<FarmLocationWithState>,
//    val status: String
)
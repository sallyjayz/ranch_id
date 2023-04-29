package com.sallyjayz.ranchid.model.state

import com.google.gson.annotations.SerializedName

data class StateResponse(
    @SerializedName("data")
    val state: List<States>
)

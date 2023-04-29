package com.sallyjayz.ranchid.model.allkeepers

import com.google.gson.annotations.SerializedName

data class AllKeepersResponse(
    @SerializedName("record")
    val record: Record
)

data class Record(
    @SerializedName("data")
    val allKeepersList: List<AllKeepers>
)

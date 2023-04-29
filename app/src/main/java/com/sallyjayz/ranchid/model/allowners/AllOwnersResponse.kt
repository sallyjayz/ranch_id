package com.sallyjayz.ranchid.model.allowners

import com.google.gson.annotations.SerializedName

data class AllOwnersResponse(
    @SerializedName("record")
    val record: Record
)

data class Record(
    @SerializedName("data")
    val allOwnersList: List<AllOwners>
)

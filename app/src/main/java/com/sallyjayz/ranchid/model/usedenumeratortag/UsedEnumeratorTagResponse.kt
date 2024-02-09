package com.sallyjayz.ranchid.model.usedenumeratortag

import com.google.gson.annotations.SerializedName

data class UsedEnumeratorTagResponse(
    @SerializedName("data")
    val usedEnumeratorTag: List<UsedEnumeratorTag>,
    val status: String
)
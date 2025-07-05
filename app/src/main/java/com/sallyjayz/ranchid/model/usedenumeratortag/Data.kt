package com.sallyjayz.ranchid.model.usedenumeratortag

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data")
    val usedEnumeratorTag: List<UsedEnumeratorTag>
)
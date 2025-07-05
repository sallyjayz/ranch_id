package com.sallyjayz.ranchid.model.unusedenumeratortag.all

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data")
    val allUnusedEnumeratorTag: List<AllUnusedEnumeratorTag>
)
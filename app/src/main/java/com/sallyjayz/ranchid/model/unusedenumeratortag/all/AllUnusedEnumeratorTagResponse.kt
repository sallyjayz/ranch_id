package com.sallyjayz.ranchid.model.unusedenumeratortag.all

import com.google.gson.annotations.SerializedName

data class AllUnusedEnumeratorTagResponse(
    /*val current_page: Int,
    val current_page_count: Int,*/
    @SerializedName("data")
    val allUnusedEnumeratorTag: List<AllUnusedEnumeratorTag>,
    val status: String,
    /*val total_count: Int,
    val total_pages: Int*/
)
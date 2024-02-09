package com.sallyjayz.ranchid.model.report.owner

import com.google.gson.annotations.SerializedName

/**
 * Created by Salama Jatau on 14-Jul-23.
 */
data class OwnerListResponse (
    val current_page: Int,
    val current_page_count: Int,
    @SerializedName("data")
    val owners: List<Owner>,
    val status: String
)
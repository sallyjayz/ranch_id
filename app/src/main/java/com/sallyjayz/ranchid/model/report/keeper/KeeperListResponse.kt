package com.sallyjayz.ranchid.model.report.keeper

import com.google.gson.annotations.SerializedName

/**
 * Created by Salama Jatau on 14-Jul-23.
 */
data class KeeperListResponse(
    val current_page: Int,
    val current_page_count: Int,
    @SerializedName("data")
    val keepers: List<Keeper>,
    val status: String,
    val total_count: Int,
    val total_pages: Int
)

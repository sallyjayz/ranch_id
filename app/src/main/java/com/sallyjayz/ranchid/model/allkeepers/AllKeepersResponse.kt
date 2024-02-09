package com.sallyjayz.ranchid.model.allkeepers

import com.google.gson.annotations.SerializedName


data class AllKeepersResponse(
    val current_page: Int,
    val current_page_count: Int,
//    val `data`: List<Data>,
    @SerializedName("data")
    val allKeepersList: List<AllKeepers>,
    val status: String,
    val total_count: Int,
    val total_pages: Int
)

/* old response from server
data class AllKeepersResponse(
    @SerializedName("record")
    val record: Record
)

data class Record(
    @SerializedName("data")
    val allKeepersList: List<AllKeepers>
)
*/

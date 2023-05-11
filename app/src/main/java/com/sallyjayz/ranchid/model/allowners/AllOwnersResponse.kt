package com.sallyjayz.ranchid.model.allowners

import com.google.gson.annotations.SerializedName

data class AllOwnersResponse(
    /*val current_page: Int,
    val current_page_count: Int,
    val `data`: List<Data>,
    val status: String,
    val total_count: Int,
    val total_pages: Int*/
    @SerializedName("data")
    val allOwnersList: List<AllOwners>
)

/*old response from the server
data class AllOwnersResponse(
    @SerializedName("record")
    val record: Record
)

data class Record(
    @SerializedName("data")
    val allOwnersList: List<AllOwners>
)
*/

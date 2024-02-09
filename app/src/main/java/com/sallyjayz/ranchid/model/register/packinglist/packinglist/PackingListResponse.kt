package com.sallyjayz.ranchid.model.register.packinglist.packinglist

data class PackingListResponse(
    val `data`: List<PackingList>,
    val message: String,
    val status: String
)
package com.sallyjayz.ranchid.model.register.packinglist.postpackinglist


data class PackingListPost(
    val driver_name: String,
    val vehicle_name: String,
    val registration_number: String,
    val livestock_type: String,
    val state: String,
    val lga: String,
    val destination_address: String,
    val tags: List<String>
)








package com.sallyjayz.ranchid.model.register.packinglist.postpackinglist

/**
 * Created by Salama Jatau on 27-Jun-23.
 */

data class Data(
    val created_at: String,
    val created_by: String,
    val del_flg: String,
    val deleted_by: String,
    val destination_address: String,
    val driver_name: String,
    val lga: String,
    val livestock_type: String,
    val registration_number: String,
    val state: String,
    val id: Int,
    val status: String,
    val tags: List<String>,
    val updated_at: String,
    val vehicle_name: String
)
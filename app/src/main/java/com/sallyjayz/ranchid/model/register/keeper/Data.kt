package com.sallyjayz.ranchid.model.register.keeper

data class Data(
    val surname: String,
    val other_names: String,
    val gender: String,
    val phone_number: String,
    val email_address: String,
    val nin: String,
    val address: String,
    val dob: String,
    val marital_status: String,
    val next_of_kin: String,
    val next_of_kin_number: String,
    val state: String,
    val lga: String,
    val ward: String,
    val passport_photo: String,
    val id_doc: String,
    val prof_id_doc: String,
    val location: String,
    val captured_by: String,
    val other_location: String,
    val updated_at: String,
    val created_at: String,
    val id: Int
)

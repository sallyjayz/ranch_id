package com.sallyjayz.ranchid.model.allkeepers

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "allkeepers")
data class AllKeepers(
    @PrimaryKey
    val id: Int,
    val surname: String,
    val other_names: String,
    /*val gender: String,
    val dob: String,
    val phone_number: String,
    val marital_status: String,
    val next_of_kin: String,
    val next_of_kin_number: String,
    val email_address: String,
    val nin: String,
    val state: String,
    val lga: String,
    val ward: String,
    val passport_photo: String,
    val id_doc: String,
    val prof_id_doc: String,
    val location: String,
    val timestamp: String,
    val captured_by: String,
    val livestock_owner: String,
    val created_at: String,
    val updated_at: String,
    val photo_url: String,
    val other_location: String,
    val address: String*/
)

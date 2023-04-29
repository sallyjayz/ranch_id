package com.sallyjayz.ranchid.model.allowners

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "allowners")
data class AllOwners(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("other_names")
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
    val captured_by: String,
    val created_at: String,
    val updated_at: String,
    val photo_url: String,
    val other_location: String,
    val ownership_type: String,
    val group_name: String*/
)

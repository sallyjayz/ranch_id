package com.sallyjayz.ranchid.model.generalInformation.generateinformation.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Custodian(
    val address: String?,
    /*val captured_by: String?,
    val created_at: String?,
    val del_flg: String?,*/
    val dob: String,
    val email_address: String?,
    val gender: String,
    val group_name: String?,
    val id: Int,
    val id_doc: String,
    val lga: String,
    val location: String,
    val marital_status: String?,
    val next_of_kin: String,
    val next_of_kin_number: String,
    val nin: String,
    val other_location: String?,
    val other_names: String,
    val ownership_type: String?,
    val passport_photo: String,
    val phone_number: String,
    val photo_url: String?,
    val prof_id_doc: String,
    val state: String,
    val surname: String,
    /*val updated_at: String?,*/
    val ward: String?
): Parcelable

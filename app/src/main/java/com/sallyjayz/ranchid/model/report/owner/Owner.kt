package com.sallyjayz.ranchid.model.report.owner

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Salama Jatau on 14-Jul-23.
 */

@Parcelize
data class Owner(
    val id: Int,
    val surname: String,
    val other_names: String,
    val gender: String,
    val dob: String,
    val phone_number: String,
    val marital_status: String,
    val next_of_kin: String,
    val next_of_kin_number: String,
    val email_address: String,
    val nin: String,
    val state: String,
    val lga: String,
    /*val ward: String,*/
    val passport_photo: String,
    val id_doc: String,
    val prof_id_doc: String,
    val location: String,
    /*val captured_by: String,
    val created_at: String,
    val updated_at: String,
    val photo_url: String,
    val other_location: String,*/
    val ownership_type: String,
    /*val group_name: String*/
): Parcelable

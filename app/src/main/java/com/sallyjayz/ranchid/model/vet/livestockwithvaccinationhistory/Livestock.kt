package com.sallyjayz.ranchid.model.vet.livestockwithvaccinationhistory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Livestock(
    /*val assigned_flg: String,
    val capture_location: String,
    val capture_state: String,
    val date_tagged: String,
    val enumerator: String,
    val exit_date: String,
    val exit_type: String,*/
    val gender: String?,
    val gestation_date: String?,
//    val keeper_id: String,
    val keeper_other_names: String?,
//    val keeper_phone_number: String,
    val keeper_surname: String?,
    val livestock_breed: String?,
    val livestock_type: String?,
    /*val missing_flg: String,
    val muzzle_photo: String,
    val owner_id: String,*/
    val owner_other_names: String?,
//    val owner_phone_number: String,
    val owner_surname: String?,
    val passport_id: String?,
//    val published_flg: String,
//    val sold_flg: String,
    val tag_id: String?,
    /*val tag_state: String,
    val verification_photo: String*/
): Parcelable
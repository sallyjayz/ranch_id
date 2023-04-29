package com.sallyjayz.ranchid.model.register.keeper

import com.google.gson.annotations.SerializedName

data class Keeper (
    val surname: String,
    @SerializedName("other_names")
    val otherNames: String,
    val gender: String,
    val dob: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val marital_status: String,
    @SerializedName("next_of_kin")
    val nextOfKin: String,
    @SerializedName("next_of_kin_number")
    val nextOfKinPhoneNumber: String,
    @SerializedName("email_address")
    val emailAddress: String,
    val nin: String,
    val state: String,
    val lga: String,
    val ward: String,
    @SerializedName("id_doc")
    val documentID: String,
    @SerializedName("prof_id_doc")
    val documentNumber: String,
    val location: String,
    val address: String,
    val other_location: String,
    @SerializedName("passport_photo")
    val photo: String,
    val timestamp: String,
    @SerializedName("captured_by")
    val capturedBy: String
)
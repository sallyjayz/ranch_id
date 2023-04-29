package com.sallyjayz.ranchid.model.offline.owner

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "offline_owner")
@Parcelize
data class OfflineOwner(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
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
    @SerializedName("ownership_type")
    val ownershipType: String,
    val group_name: String,
    val timestamp: String,
    @SerializedName("captured_by")
    val capturedBy: String,
    @SerializedName("livestock_keeper")
    val livestockKeeper: Boolean,
    val status: String
) : Parcelable

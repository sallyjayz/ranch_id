package com.sallyjayz.ranchid.model.offline.taglivestock

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tag_livestock")
@Parcelize
data class OfflineTagLivestock(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val latitude: String,
    val longitude: String,
    @SerializedName("tag_id")
    val tagId: String,
    @SerializedName("passport_id")
    val passportId: String,
    @SerializedName("keeper_id")
    val keeperId: String,
    @SerializedName("owner_id")
    val ownerId: String,
    @SerializedName("livestock_type")
    val livestockType: String,
    @SerializedName("livestock_breed")
    val livestockBreed: String,
    val gender: String,
    @SerializedName("health_status")
    val healthStatus: String,
    @SerializedName("gestation_date")
    val gestationDate: String,
    val description: String,
    @SerializedName("tagging_loc_id")
    val taggingLocId: String,
    val weight: String,
    @SerializedName("production_type")
    val productionType: String,
    val enumerator: String,
    @SerializedName("other_comments")
    val otherComments: String,
    @SerializedName("scan_purpose")
    val scanPurpose: String,
    @SerializedName("verification_photo")
    val verificationPhoto: String,
    @SerializedName("muzzle_photo")
    val muzzlePhoto: String,
    val status: String
) : Parcelable

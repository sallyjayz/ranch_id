package com.sallyjayz.ranchid.model.register.packinglist.scannedlivestock

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "livestock_data")
@Parcelize
data class ScanLivestock(
    @PrimaryKey(autoGenerate = false)
    val tag_id: String,
    val livestock_type: String,
    val livestock_breed: String,
    val gender: String,
    val gestation_date: String
): Parcelable
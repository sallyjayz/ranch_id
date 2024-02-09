package com.sallyjayz.ranchid.model.report.location

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val id: Int,
    /*val latitude: String,*/
    val lga: Int,
    val location_name: String,
    val location_type: String,
    /*val longitude: String,*/
    val state: Int
): Parcelable
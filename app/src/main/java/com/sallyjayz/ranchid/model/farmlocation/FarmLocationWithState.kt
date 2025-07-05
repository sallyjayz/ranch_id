package com.sallyjayz.ranchid.model.farmlocation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "farm_location_state_lga")
data class FarmLocationWithState(
    @PrimaryKey
    val id: Int,
    val latitude: String,
    val lga: Int,
    val location_name: String,
    val location_type: String,
    val longitude: String,
    val state: Int
)
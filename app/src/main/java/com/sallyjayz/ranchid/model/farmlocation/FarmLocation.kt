package com.sallyjayz.ranchid.model.farmlocation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FarmLocation")
data class FarmLocation(
    @PrimaryKey
    val id: Int,
    val location_name: String,
    val location_type: String,

)


package com.sallyjayz.ranchid.model.vet.vetdashboard

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vaccinate_livestock")
data class Vaccinations(
    @PrimaryKey
    val id: Int,
    val thisMonth: Int,
    val thisWeek: Int,
    val today: Int,
    val total: Int
)
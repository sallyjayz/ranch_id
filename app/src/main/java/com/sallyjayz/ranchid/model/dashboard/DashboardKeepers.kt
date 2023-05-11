package com.sallyjayz.ranchid.model.dashboard

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dashboard_keeper")
data class DashboardKeepers(
    @PrimaryKey
    val id: Int,
    val lifetime: Int,
    val thisMonth: Int,
    val thisWeek: Int,
    val today: Int
)
package com.sallyjayz.ranchid.model.dashboard

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dashboard_tagged")
data class DashboardTagged(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val lifetime: Int,
    val thisMonth: Int,
    val thisWeek: Int,
    val today: Int
)
package com.sallyjayz.ranchid.model.allkeepers

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "allkeepers")
data class AllKeepers(
    @PrimaryKey
    val id: Int,
    val surname: String,
    val other_names: String
)

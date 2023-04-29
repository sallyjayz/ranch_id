package com.sallyjayz.ranchid.model.unusedpassport

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="unused_passport")
data class UnusedPassport(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val passportId: String
)

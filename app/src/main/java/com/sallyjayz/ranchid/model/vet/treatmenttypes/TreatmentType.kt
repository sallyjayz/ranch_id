package com.sallyjayz.ranchid.model.vet.treatmenttypes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "treatment_type")
data class TreatmentType(
    @PrimaryKey
    val id: Int,
    val type: String,
    val variants: String
)
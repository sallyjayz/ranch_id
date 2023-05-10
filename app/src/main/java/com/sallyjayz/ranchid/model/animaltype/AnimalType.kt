package com.sallyjayz.ranchid.model.animaltype

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animal_type")
data class AnimalType(
    @PrimaryKey
    val id: Int,
    val name: String
)
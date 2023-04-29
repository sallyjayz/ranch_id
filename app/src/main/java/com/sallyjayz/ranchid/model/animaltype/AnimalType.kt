package com.sallyjayz.ranchid.model.animaltype

import androidx.room.Entity

@Entity(tableName = "animal_type")
data class AnimalType(
    val id: Int,
    val name: String
)
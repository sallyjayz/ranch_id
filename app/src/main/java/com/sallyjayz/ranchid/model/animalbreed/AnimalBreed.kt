package com.sallyjayz.ranchid.model.animalbreed

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animal_breed")
data class AnimalBreed(
    val animal_tag_type: String,
    val animal_type_id: Int,
    @PrimaryKey
    val id: Int,
    val name: String
)
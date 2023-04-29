package com.sallyjayz.ranchid.model.animalbreed

import androidx.room.Entity

@Entity(tableName = "animal_breed")
data class AnimalBreed(
    val animal_tag_type: String,
    val animal_type_id: Int,
    val id: Int,
    val name: String
)
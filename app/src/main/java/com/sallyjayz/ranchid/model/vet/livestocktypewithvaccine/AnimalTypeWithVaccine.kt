package com.sallyjayz.ranchid.model.vet.livestocktypewithvaccine

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "animal_type_with_vaccine")
data class AnimalTypeWithVaccine(
    @SerializedName("animal_type")
    val animalType: String,
    @PrimaryKey
    val id: Int,
    @SerializedName("vaccine_name")
    val vaccineName: String
)
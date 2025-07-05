package com.sallyjayz.ranchid.model.vet.livestocktypewithvaccine

data class LivestockTypeWithVaccineResponse(
    val `data`: List<AnimalTypeWithVaccine>,
    val message: String,
    val status: String,
    val success: Boolean
)
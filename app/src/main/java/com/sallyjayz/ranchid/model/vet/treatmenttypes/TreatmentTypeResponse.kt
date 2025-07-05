package com.sallyjayz.ranchid.model.vet.treatmenttypes

data class TreatmentTypeResponse(
    val `data`: List<TreatmentType>,
    val message: String,
    val status: String,
    val success: Boolean
)
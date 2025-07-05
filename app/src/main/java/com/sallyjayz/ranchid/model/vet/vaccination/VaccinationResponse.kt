package com.sallyjayz.ranchid.model.vet.vaccination

import com.google.gson.annotations.SerializedName

data class VaccinationResponse(
    @SerializedName("data")
//    val `data`: Data,
    val vaccination: Vaccination,
    val message: String,
    val status: String
)
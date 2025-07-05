package com.sallyjayz.ranchid.model.vet.treatment

import com.google.gson.annotations.SerializedName

data class TreatmentResponse(
    @SerializedName("data")
//    val `data`: Data,
    val treatment: Treatment,
    val message: String,
    val status: String,
    val success: Boolean
)
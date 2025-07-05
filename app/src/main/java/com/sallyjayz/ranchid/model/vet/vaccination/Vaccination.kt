package com.sallyjayz.ranchid.model.vet.vaccination

import com.google.gson.annotations.SerializedName

/**
 * Created by Salama Jatau on 23-Mar-24.
 */
data class Vaccination (
    @SerializedName("date_of_vaccination")
    val dateOfVaccination: String,
    val dosage: String,
    @SerializedName("drug_id")
    val drugId: String,
    val frequency: String,
//    val id: Int,
    @SerializedName("next_appointment")
    val nextAppointment: String,
    val notes: String,
    @SerializedName("tag_id")
    val tagId: String,
    @SerializedName("time_of_vaccination")
    val timeOfVaccination: String,
    @SerializedName("vaccination_image")
    val vaccinationImage: String,
    @SerializedName("vet_council_number")
    val vetCouncilNumber: String
)
package com.sallyjayz.ranchid.model.vet.treatment

import com.google.gson.annotations.SerializedName

data class Treatment(
    @SerializedName("administration_route")
    val administrationRoute: String,
    @SerializedName("date_of_treatment")
    val dateOfTreatment: String,
    val diagnosis: String,
    val dosage: String,
    val drug: String,
    @SerializedName("follow_up_date")
    val followUpDate: String,
    val frequency: String,
//    val id: Int,
    @SerializedName("injection_type")
    val injectionType: String,
    val notes: String,
    @SerializedName("tag_id")
    val tagId: String,
    @SerializedName("time_of_treatment")
    val timeOfTreatment: String,
    @SerializedName("treatment_category")
    val treatmentCategory: String,
    @SerializedName("treatment_image")
    val treatmentImage: String,
    @SerializedName("treatment_type")
    val treatmentType: String,
    @SerializedName("vet_council_number")
    val vetCouncilNumber: String
)
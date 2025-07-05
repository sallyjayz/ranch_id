package com.sallyjayz.ranchid.model.vet.livestockwithvaccinationhistory

data class VaccinationHistory(
    val date_of_vaccination: String,
    val dosage: String,
    val drug: String,
    val frequency: String,
    val id: Int,
    val next_appointment: String,
    val notes: String,
    val tag_id: String,
    val time_of_vaccination: String,
    val vet_council_number: String
)
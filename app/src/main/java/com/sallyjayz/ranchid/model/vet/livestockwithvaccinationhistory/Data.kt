package com.sallyjayz.ranchid.model.vet.livestockwithvaccinationhistory

data class Data(
    val livestock: Livestock,
    val vaccination_history: List<VaccinationHistory>
)
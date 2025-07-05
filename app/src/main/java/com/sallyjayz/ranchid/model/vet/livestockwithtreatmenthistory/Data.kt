package com.sallyjayz.ranchid.model.vet.livestockwithtreatmenthistory

data class Data(
    val livestock: Livestock,
    val vaccination_history: List<VaccinationHistory>
)
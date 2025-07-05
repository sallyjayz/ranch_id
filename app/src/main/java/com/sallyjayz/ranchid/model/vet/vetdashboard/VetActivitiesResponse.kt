package com.sallyjayz.ranchid.model.vet.vetdashboard

data class VetActivitiesResponse(
    val message: String,
    val status: String,
    val upcoming_appointments: UpcomingAppointments,
    val vaccinations: Vaccinations
)
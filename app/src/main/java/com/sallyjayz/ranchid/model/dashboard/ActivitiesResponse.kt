package com.sallyjayz.ranchid.model.dashboard

data class ActivitiesResponse(
    val keepers: DashboardKeepers,
    val livestock: DashboardLivestock,
    val owners: DashboardOwners,
    val status: String
)
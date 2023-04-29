package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.DashboardActivitiesDao
import com.sallyjayz.ranchid.model.dashboard.DashboardExit
import com.sallyjayz.ranchid.model.dashboard.DashboardKeepers
import com.sallyjayz.ranchid.model.dashboard.DashboardOwners
import com.sallyjayz.ranchid.model.dashboard.DashboardTagged
import com.sallyjayz.ranchid.service.dashboard.DashboardActivitiesApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class DashboardActivitiesRepository @Inject constructor(
    private val dashboardActivitiesApiService: DashboardActivitiesApiService,
    private val dashboardActivitiesDao: DashboardActivitiesDao

) {

    fun getDashboardActivities(username: String) = apiRequestFlow {
        dashboardActivitiesApiService.getAllActivities(username)
    }

    suspend fun saveDashboardKeepers(keeper: DashboardKeepers) =
        dashboardActivitiesDao.insertDashboardKeepers(keeper)

    suspend fun saveDashboardOwners(owner: DashboardOwners) =
        dashboardActivitiesDao.insertDashboardOwners(owner)

    suspend fun saveDashboardExit(exit: DashboardExit) =
        dashboardActivitiesDao.insertDashboardExit(exit)

    suspend fun saveDashboardTagged(tagged: DashboardTagged) =
        dashboardActivitiesDao.insertDashboardTagged(tagged)

    val readKeeper: LiveData<DashboardKeepers> =
        dashboardActivitiesDao.readAllDashboardKeeper()

    val readOwner: LiveData<DashboardOwners> =
        dashboardActivitiesDao.readAllDashboardOwner()

    val readExit: LiveData<DashboardExit> =
        dashboardActivitiesDao.readAllDashboardExit()

    val readTagged: LiveData<DashboardTagged> =
        dashboardActivitiesDao.readAllDashboardTagged()

    fun deleteDashboardKeeper() = dashboardActivitiesDao.deleteDashboardKeeper()

    fun deleteDashboardOwner() = dashboardActivitiesDao.deleteDashboardOwner()

    fun deleteDashboardExit() = dashboardActivitiesDao.deleteDashboardExit()

    fun deleteDashboardTagged() = dashboardActivitiesDao.deleteDashboardTagged()
}
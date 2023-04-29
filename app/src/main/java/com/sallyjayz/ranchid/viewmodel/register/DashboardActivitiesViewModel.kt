package com.sallyjayz.ranchid.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.model.dashboard.DashboardExit
import com.sallyjayz.ranchid.model.dashboard.DashboardKeepers
import com.sallyjayz.ranchid.model.dashboard.DashboardOwners
import com.sallyjayz.ranchid.model.dashboard.DashboardTagged
import com.sallyjayz.ranchid.repository.DashboardActivitiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardActivitiesViewModel @Inject constructor(
    private val dashboardActivitiesRepository: DashboardActivitiesRepository,
    context: Application
): AndroidViewModel(context) {

    private var _readKeeper: LiveData<DashboardKeepers> =
        dashboardActivitiesRepository.readKeeper
    val readKeeper: LiveData<DashboardKeepers>
        get() = _readKeeper

    private var _readOwner: LiveData<DashboardOwners> =
        dashboardActivitiesRepository.readOwner
    val readOwner: LiveData<DashboardOwners>
        get() = _readOwner

    private var _readExit: LiveData<DashboardExit> =
        dashboardActivitiesRepository.readExit
    val readExit: LiveData<DashboardExit>
        get() = _readExit

    private var _readTagged: LiveData<DashboardTagged> =
        dashboardActivitiesRepository.readTagged
    val readTagged: LiveData<DashboardTagged>
        get() = _readTagged

    suspend fun insertKeeper(keepers: DashboardKeepers) =
        dashboardActivitiesRepository.saveDashboardKeepers(keepers)

    suspend fun insertOwner(owners: DashboardOwners) =
        dashboardActivitiesRepository.saveDashboardOwners(owners)

    suspend fun insertExit(exit: DashboardExit) =
        dashboardActivitiesRepository.saveDashboardExit(exit)

    suspend fun insertTagged(tagged: DashboardTagged) =
        dashboardActivitiesRepository.saveDashboardTagged(tagged)

    fun deleteKeeper() = dashboardActivitiesRepository.deleteDashboardKeeper()

    fun deleteOwner() = dashboardActivitiesRepository.deleteDashboardOwner()

    fun deleteExit() = dashboardActivitiesRepository.deleteDashboardExit()

    fun deleteTagged() = dashboardActivitiesRepository.deleteDashboardTagged()

}
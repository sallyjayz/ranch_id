package com.sallyjayz.ranchid.repository.vet

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.VetDashboardActivitiesDao
import com.sallyjayz.ranchid.model.vet.vetdashboard.UpcomingAppointments
import com.sallyjayz.ranchid.model.vet.vetdashboard.Vaccinations
import com.sallyjayz.ranchid.service.vet.VetDashboardActivitiesApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

/**
 * Created by Salama Jatau on 19-Mar-24.
 */
class VetDashboardActivitiesRepository @Inject constructor(
    private val vetDashboardActivitiesApiService: VetDashboardActivitiesApiService,
    private val vetDashboardActivitiesDao: VetDashboardActivitiesDao
) {

    fun getVetDashboardActivities() = apiRequestFlow {
        vetDashboardActivitiesApiService.getAllVetActivities()
    }

    suspend fun saveDashboardAppointment(appointment: UpcomingAppointments) =
        vetDashboardActivitiesDao.insertVetDashboardAppointment(appointment)

    suspend fun saveDashboardVaccination(vaccination: Vaccinations) =
        vetDashboardActivitiesDao.insertVetDashboardVaccination(vaccination)

    val readDashboardAppointment: LiveData<UpcomingAppointments> =
        vetDashboardActivitiesDao.readAllVetDashboardAppointment()

    val readDashboardVaccination: LiveData<Vaccinations> =
        vetDashboardActivitiesDao.readAllVetDashboardVaccination()

    fun deleteDashboardAppointment() = vetDashboardActivitiesDao.deleteVetDashboardAppointment()

    fun deleteDashboardVaccination() = vetDashboardActivitiesDao.deleteVetDashboardVaccination()

}
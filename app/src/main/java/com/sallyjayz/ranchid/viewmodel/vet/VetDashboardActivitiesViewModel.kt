package com.sallyjayz.ranchid.viewmodel.vet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.model.vet.vetdashboard.UpcomingAppointments
import com.sallyjayz.ranchid.model.vet.vetdashboard.Vaccinations
import com.sallyjayz.ranchid.repository.vet.VetDashboardActivitiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 19-Mar-24.
 */

@HiltViewModel
class VetDashboardActivitiesViewModel @Inject constructor(
    private val vetDashboardActivitiesRepository: VetDashboardActivitiesRepository,
    context: Application
): AndroidViewModel(context) {

    private var _readAppointment: LiveData<UpcomingAppointments> =
            vetDashboardActivitiesRepository.readDashboardAppointment
    val readAppointment: LiveData<UpcomingAppointments>
        get() = _readAppointment

    private var _readVaccination: LiveData<Vaccinations> =
        vetDashboardActivitiesRepository.readDashboardVaccination
    val readVaccination: LiveData<Vaccinations>
        get() = _readVaccination

    suspend fun insertDashboardAppointment(appointments: UpcomingAppointments) =
        vetDashboardActivitiesRepository.saveDashboardAppointment(appointments)

    suspend fun insertDashboardVaccination(vaccinations: Vaccinations) =
        vetDashboardActivitiesRepository.saveDashboardVaccination(vaccinations)

    fun deleteAppointment() = vetDashboardActivitiesRepository.deleteDashboardAppointment()

    fun deleteVaccination() = vetDashboardActivitiesRepository.deleteDashboardVaccination()

}
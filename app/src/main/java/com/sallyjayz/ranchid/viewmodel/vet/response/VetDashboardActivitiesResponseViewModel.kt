package com.sallyjayz.ranchid.viewmodel.vet.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.vet.vetdashboard.VetActivitiesResponse
import com.sallyjayz.ranchid.repository.vet.VetDashboardActivitiesRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 19-Mar-24.
 */

@HiltViewModel
class VetDashboardActivitiesResponseViewModel @Inject constructor(
    private val vetDashboardActivitiesRepository: VetDashboardActivitiesRepository
): BaseViewModel() {

    private val _activitiesResponse = MutableLiveData<ApiResponse<VetActivitiesResponse>>()
    val activitiesResponse = _activitiesResponse

    fun getVetActivities(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _activitiesResponse,
        coroutinesErrorHandler
    ) {
        vetDashboardActivitiesRepository.getVetDashboardActivities()
    }

}
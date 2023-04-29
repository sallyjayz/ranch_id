package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.dashboard.ActivitiesResponse
import com.sallyjayz.ranchid.repository.DashboardActivitiesRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardActivitiesResponseViewModel @Inject constructor(
    private val dashboardActivitiesRepository: DashboardActivitiesRepository
): BaseViewModel() {

    private val _activitiesResponse = MutableLiveData<ApiResponse<ActivitiesResponse>>()
    val activitiesResponse = _activitiesResponse

    fun getActivities(username: String, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _activitiesResponse,
        coroutinesErrorHandler
    ) {
        dashboardActivitiesRepository.getDashboardActivities(username)
    }

}
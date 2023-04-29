package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.farmlocation.FarmLocationResponse
import com.sallyjayz.ranchid.repository.FarmLocationRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FarmLocationResponseViewModel @Inject constructor(
    private val farmLocationRepository: FarmLocationRepository
): BaseViewModel() {

    private val _farmLocationResponse = MutableLiveData<ApiResponse<FarmLocationResponse>>()
    val farmLocationResponse = _farmLocationResponse

    fun getLocation(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _farmLocationResponse,
        coroutinesErrorHandler
    ) {
        farmLocationRepository.getFarmLocation()
    }

}
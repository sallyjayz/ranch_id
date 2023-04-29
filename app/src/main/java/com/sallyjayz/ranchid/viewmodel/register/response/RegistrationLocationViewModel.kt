package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.register.registrationlocation.RegistrationLocation
import com.sallyjayz.ranchid.model.register.registrationlocation.RegistrationLocationResponse
import com.sallyjayz.ranchid.repository.RegistrationLocationRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationLocationViewModel @Inject constructor(
    private val registrationLocationRepository: RegistrationLocationRepository
): BaseViewModel() {

    private val _addRegistrationLocationResponse = MutableLiveData<ApiResponse<RegistrationLocationResponse>>()
    val addRegistrationLocationResponse = _addRegistrationLocationResponse

    fun addRegistrationLocation(registrationLocation: RegistrationLocation, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _addRegistrationLocationResponse,
        coroutinesErrorHandler
    ) {
        registrationLocationRepository.addRegistrationLocation(registrationLocation)
    }

}
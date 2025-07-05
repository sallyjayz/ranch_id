package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.farmlocation.FarmLocationWithStateResponse
import com.sallyjayz.ranchid.repository.FarmLocationWithStateRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 16-Jan-25.
 */

@HiltViewModel
class FarmLocationWithStateResponseViewModel @Inject constructor(
    private val farmLocationWithStateRepository: FarmLocationWithStateRepository
): BaseViewModel() {

    private val _farmLocationWithStateResponse = MutableLiveData<ApiResponse<FarmLocationWithStateResponse>>()
    val farmLocationWithStateResponse = _farmLocationWithStateResponse

    fun getLocationWithState(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _farmLocationWithStateResponse,
        coroutinesErrorHandler
    ) {
        farmLocationWithStateRepository.getFarmLocationWithState()
    }

}
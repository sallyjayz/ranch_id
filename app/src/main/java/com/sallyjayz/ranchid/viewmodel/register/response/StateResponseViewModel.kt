package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.state.StateResponse
import com.sallyjayz.ranchid.repository.StateRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StateResponseViewModel @Inject constructor(
    private val stateRepository: StateRepository
): BaseViewModel() {

    private val _stateResponse = MutableLiveData<ApiResponse<StateResponse>>()
    val stateResponse = _stateResponse

    fun getState(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _stateResponse,
        coroutinesErrorHandler
    ) {
        stateRepository.getStates()
    }

}
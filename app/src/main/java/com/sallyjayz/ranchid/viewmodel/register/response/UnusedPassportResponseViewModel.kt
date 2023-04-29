package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.unusedpassport.UnusedPassportResponse
import com.sallyjayz.ranchid.repository.UnusedPassportRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnusedPassportResponseViewModel @Inject constructor(
    private val unusedPassportRepository: UnusedPassportRepository
): BaseViewModel() {

    private val _unusedPassportResponse = MutableLiveData<ApiResponse<UnusedPassportResponse>>()
    val unusedPassportResponse = _unusedPassportResponse

    fun getUnusedPassport(username: String, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _unusedPassportResponse,
        coroutinesErrorHandler
    ) {
        unusedPassportRepository.getUnusedPassport(username)
    }

}
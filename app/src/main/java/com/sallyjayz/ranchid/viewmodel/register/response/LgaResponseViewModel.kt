package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.lga.LGAResponse
import com.sallyjayz.ranchid.repository.LgaRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LgaResponseViewModel@Inject constructor(
    private val lgaRepository: LgaRepository
): BaseViewModel() {

    private val _lgaResponse = MutableLiveData<ApiResponse<LGAResponse>>()
    val lgaResponse = _lgaResponse

    fun getLGA(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _lgaResponse,
        coroutinesErrorHandler
    ) {
        lgaRepository.getLGAs()
    }

}
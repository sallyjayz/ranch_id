package com.sallyjayz.ranchid.viewmodel.vet.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.vet.livestockwithvaccinationhistory.LivestockWithVaccinationHistoryResponse
import com.sallyjayz.ranchid.repository.vet.LivestockWithVaccinationHistoryRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 22-Mar-24.
 */

@HiltViewModel
class LivestockWithVaccinationHistoryResponseViewModel @Inject constructor(
    private val livestockWithVaccinationHistoryRepository: LivestockWithVaccinationHistoryRepository
): BaseViewModel() {

    private val _livestockWithVaccinationHistoryResponse = MutableLiveData<ApiResponse<LivestockWithVaccinationHistoryResponse>>()
    val livestockWithVaccinationHistoryResponse = _livestockWithVaccinationHistoryResponse

    fun getLivestockWithVaccinationHistory(tagId: String, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _livestockWithVaccinationHistoryResponse,
        coroutinesErrorHandler
    ) {
        livestockWithVaccinationHistoryRepository.getLivestockWithVaccinationHistory(tagId)
    }

}
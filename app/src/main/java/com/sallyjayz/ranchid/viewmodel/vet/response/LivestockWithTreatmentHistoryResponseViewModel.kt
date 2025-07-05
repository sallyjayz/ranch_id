package com.sallyjayz.ranchid.viewmodel.vet.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.vet.livestockwithtreatmenthistory.LivestockWithTreatmentHistoryResponse
import com.sallyjayz.ranchid.repository.vet.LivestockWithTreatmentHistoryRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 27-Jun-24.
 */
@HiltViewModel
class LivestockWithTreatmentHistoryResponseViewModel @Inject constructor(
    private val livestockWithTreatmentHistoryRepository : LivestockWithTreatmentHistoryRepository
): BaseViewModel() {
    private val _livestockWithTreatmentHistoryResponse = MutableLiveData < ApiResponse <LivestockWithTreatmentHistoryResponse>>()
    val livestockWithTreatmentHistoryResponse = _livestockWithTreatmentHistoryResponse

    fun getLivestockWithTreatmentHistory(tagId: String, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _livestockWithTreatmentHistoryResponse,
        coroutinesErrorHandler
    ) {
        livestockWithTreatmentHistoryRepository.getLivestockWithTreatmentHistory(tagId)
    }
}
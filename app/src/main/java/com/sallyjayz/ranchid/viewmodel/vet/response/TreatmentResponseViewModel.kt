package com.sallyjayz.ranchid.viewmodel.vet.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.vet.treatment.Treatment
import com.sallyjayz.ranchid.model.vet.treatment.TreatmentResponse
import com.sallyjayz.ranchid.repository.vet.TreatmentRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 27-Jun-24.
 */

@HiltViewModel
class TreatmentResponseViewModel @Inject constructor(
    private val treatmentRepository: TreatmentRepository
): BaseViewModel() {

    private val _treatmentResponse = MutableLiveData < ApiResponse <TreatmentResponse>>()
    val treatmentResponse = _treatmentResponse

    fun addTreatment(treatment: Treatment, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _treatmentResponse,
        coroutinesErrorHandler
    ) {
        treatmentRepository.addTreatment(treatment)
    }

}
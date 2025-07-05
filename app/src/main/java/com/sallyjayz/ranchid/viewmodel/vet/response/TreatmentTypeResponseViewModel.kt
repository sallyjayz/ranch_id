package com.sallyjayz.ranchid.viewmodel.vet.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.vet.treatmenttypes.TreatmentTypeResponse
import com.sallyjayz.ranchid.repository.vet.TreatmentTypeRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 05-Jul-24.
 */

@HiltViewModel
class TreatmentTypeResponseViewModel @Inject constructor(
    private val treatmentTypeRepository: TreatmentTypeRepository
): BaseViewModel() {

    private val _treatmentTypeResponse = MutableLiveData <ApiResponse<TreatmentTypeResponse>>()
    val treatmentTypeResponse = _treatmentTypeResponse

    fun getTreatmentType(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _treatmentTypeResponse,
        coroutinesErrorHandler
    ) {
        treatmentTypeRepository.getTreatmentType()
    }

}
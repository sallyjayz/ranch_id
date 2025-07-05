package com.sallyjayz.ranchid.viewmodel.vet.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.vet.vaccination.Vaccination
import com.sallyjayz.ranchid.model.vet.vaccination.VaccinationResponse
import com.sallyjayz.ranchid.repository.vet.VaccinationRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 23-Mar-24.
 */

@HiltViewModel
class VaccinationResponseViewModel @Inject constructor(
    private val vaccinationRepository: VaccinationRepository
): BaseViewModel() {

    private val _vaccinationResponse = MutableLiveData<ApiResponse<VaccinationResponse>>()
    val vaccinationResponse = _vaccinationResponse

    fun addVaccination(vaccination: Vaccination, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _vaccinationResponse,
        coroutinesErrorHandler
    ) {
        vaccinationRepository.addVaccination(vaccination)
    }

}
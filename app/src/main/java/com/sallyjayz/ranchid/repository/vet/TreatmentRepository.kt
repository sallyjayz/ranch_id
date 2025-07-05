package com.sallyjayz.ranchid.repository.vet

import com.sallyjayz.ranchid.model.vet.treatment.Treatment
import com.sallyjayz.ranchid.service.vet.TreatmentApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

/**
 * Created by Salama Jatau on 27-Jun-24.
 */
class TreatmentRepository @Inject constructor(
    private val treatmentApiService: TreatmentApiService
) {

    fun addTreatment(treatment: Treatment) = apiRequestFlow {
        treatmentApiService.addTreatment(treatment)
    }

}
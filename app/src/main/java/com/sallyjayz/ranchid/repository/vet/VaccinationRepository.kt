package com.sallyjayz.ranchid.repository.vet

import com.sallyjayz.ranchid.model.vet.vaccination.Vaccination
import com.sallyjayz.ranchid.service.vet.VaccinationApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

/**
 * Created by Salama Jatau on 23-Mar-24.
 */
class VaccinationRepository @Inject constructor(
    private val vaccinationApiService: VaccinationApiService
) {

    fun addVaccination(vaccination: Vaccination) = apiRequestFlow {
        vaccinationApiService.addVaccination(vaccination)
    }

}
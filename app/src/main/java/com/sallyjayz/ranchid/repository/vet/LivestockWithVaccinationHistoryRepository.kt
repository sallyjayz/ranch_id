package com.sallyjayz.ranchid.repository.vet

import com.sallyjayz.ranchid.service.vet.LivestockWithVaccinationHistoryApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

/**
 * Created by Salama Jatau on 22-Mar-24.
 */
class LivestockWithVaccinationHistoryRepository @Inject constructor(
    private val livestockWithVaccinationHistoryApiService: LivestockWithVaccinationHistoryApiService
) {

    fun getLivestockWithVaccinationHistory(tagId: String) = apiRequestFlow {
        livestockWithVaccinationHistoryApiService.getLivestockWithVaccinationHistory(tagId)
    }

}
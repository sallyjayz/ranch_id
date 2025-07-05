package com.sallyjayz.ranchid.repository.vet

import com.sallyjayz.ranchid.service.vet.LivestockWithTreatmentHistoryApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

/**
 * Created by Salama Jatau on 27-Jun-24.
 */
class LivestockWithTreatmentHistoryRepository @Inject constructor(
    private val livestockWithTreatmentHistoryApiService: LivestockWithTreatmentHistoryApiService
) {
    fun getLivestockWithTreatmentHistory(tagId: String) = apiRequestFlow {
        livestockWithTreatmentHistoryApiService.getLivestockWithTreatmentHistory(tagId);
    }
}
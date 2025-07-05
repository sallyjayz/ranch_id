package com.sallyjayz.ranchid.repository.vet

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.VetTreatmentTypeDao
import com.sallyjayz.ranchid.model.vet.treatmenttypes.TreatmentType
import com.sallyjayz.ranchid.service.vet.TreatmentTypeApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

/**
 * Created by Salama Jatau on 05-Jul-24.
 */
class TreatmentTypeRepository @Inject constructor(
    private val treatmentTypeApiService: TreatmentTypeApiService,
    private val vetTreatmentTypeDao: VetTreatmentTypeDao
) {

    fun getTreatmentType() = apiRequestFlow {
        treatmentTypeApiService.getAllTreatmentType()
    }

    val readAll: LiveData<List<String>> =
        vetTreatmentTypeDao.readAllTreatmentType()

    suspend fun saveTreatmentType(treatmentType: List<TreatmentType>) =
        vetTreatmentTypeDao.insertTreatmentTypes(treatmentType)

    fun getTreatmentType(variant: String) =
        vetTreatmentTypeDao.readSelectedTreatmentType(variant)

    fun getTreatmentCategory(type: String) =
        vetTreatmentTypeDao.readSelectedTreatmentCategory(type)

    fun deleteAllTreatmentType() =
        vetTreatmentTypeDao.deleteAllTreatmentType()

}
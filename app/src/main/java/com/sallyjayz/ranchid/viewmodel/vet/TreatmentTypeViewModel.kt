package com.sallyjayz.ranchid.viewmodel.vet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.model.vet.treatmenttypes.TreatmentType
import com.sallyjayz.ranchid.repository.vet.TreatmentTypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 05-Jul-24.
 */

@HiltViewModel
class TreatmentTypeViewModel @Inject constructor(
    private val treatmentTypeRepository: TreatmentTypeRepository,
    context: Application
): AndroidViewModel(context) {

    private var _readAllTreatmentType : LiveData<List<String>> =
        treatmentTypeRepository.readAll

    val readAllTreatmentType : LiveData<List<String>>
        get() = _readAllTreatmentType

    fun getTreatmentType(variant: String) =
        treatmentTypeRepository.getTreatmentType(variant)

    fun getTreatmentCategory(type: String) =
        treatmentTypeRepository.getTreatmentCategory(type)

    suspend fun insertTreatmentType(treatmentType: List<TreatmentType>) =
        treatmentTypeRepository.saveTreatmentType(treatmentType)

    fun deleteAllTreatmentType() = treatmentTypeRepository.deleteAllTreatmentType()

}
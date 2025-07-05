package com.sallyjayz.ranchid.viewmodel.vet.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.vet.livestocktypewithvaccine.LivestockTypeWithVaccineResponse
import com.sallyjayz.ranchid.repository.vet.AnimalTypeWithVaccineRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 29-Jun-24.
 */
@HiltViewModel
class AnimalTypeWithVaccineResponseViewModel @Inject constructor(
    private val animalTypeWithVaccineRepository: AnimalTypeWithVaccineRepository
): BaseViewModel(){

    private val _animalTypeWithVaccineResponse = MutableLiveData <ApiResponse<LivestockTypeWithVaccineResponse>>()
    val animalTypeWithVaccineResponse = _animalTypeWithVaccineResponse

    fun getAnimalTypeWithVaccine(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _animalTypeWithVaccineResponse,
        coroutinesErrorHandler
    ) {
        animalTypeWithVaccineRepository.getAnimalTypeWithVaccine()
    }

}
package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.animaltype.AnimalTypeResponse
import com.sallyjayz.ranchid.repository.AnimalTypeRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import javax.inject.Inject

class AnimalTypeResponseViewModel @Inject constructor(
    private val animalTypeRepository: AnimalTypeRepository
): BaseViewModel() {

    private val _animalTypeResponse = MutableLiveData<ApiResponse<AnimalTypeResponse>>()
    val animalTypeResponse = _animalTypeResponse

    fun getAnimalType(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _animalTypeResponse,
        coroutinesErrorHandler
    ) {
        animalTypeRepository.getAnimalType()
    }

}
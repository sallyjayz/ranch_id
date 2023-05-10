package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.animalbreed.AnimalBreedResponse
import com.sallyjayz.ranchid.repository.AnimalBreedRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimalBreedResponseViewModel @Inject constructor(
    private val animalBreedRepository: AnimalBreedRepository
): BaseViewModel() {

    private val _animalBreedResponse = MutableLiveData<ApiResponse<AnimalBreedResponse>>()
    val animalBreedResponse = _animalBreedResponse

    fun getAnimalBreed(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _animalBreedResponse,
        coroutinesErrorHandler
    ) {
        animalBreedRepository.getAnimalBreed()
    }

}
package com.sallyjayz.ranchid.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.model.animalbreed.AnimalBreed
import com.sallyjayz.ranchid.repository.AnimalBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimalBreedViewModel @Inject constructor(
    private val animalBreedRepository: AnimalBreedRepository,
    context: Application
): AndroidViewModel(context) {

    private var _readAllAnimalBreed: LiveData<List<AnimalBreed>> = animalBreedRepository.readAll
    val readAllAnimalBreed: LiveData<List<AnimalBreed>>
        get() = _readAllAnimalBreed

    fun getAnimalBreedName(name: String) = animalBreedRepository.getAnimalBreedName(name)

    fun getAnimalTypeBreedId(id: Int) = animalBreedRepository.getAnimalTypeBreedId(id)

    suspend fun insertAnimalBreed(animalBreed: List<AnimalBreed>) =
        animalBreedRepository.saveAnimalBreed(animalBreed)

    fun deleteAllBreed() = animalBreedRepository.deleteAllAnimalBreed()

}
package com.sallyjayz.ranchid.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.model.animaltype.AnimalType
import com.sallyjayz.ranchid.repository.AnimalTypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimalTypeViewModel @Inject constructor(
    private val animalTypeRepository: AnimalTypeRepository,
    context: Application
): AndroidViewModel(context) {

    private var _readAllAnimalType : LiveData<List<AnimalType>> = animalTypeRepository.readAll
    val readAllAnimalType: LiveData<List<AnimalType>>
        get() = _readAllAnimalType

    fun getAnimalTypeName(name: String) =
        animalTypeRepository.getAnimalTypeName(name)

    fun getAnimalTagType(type: String) =
        animalTypeRepository.getAnimalTagType(type)

    suspend fun insertAnimalType(animalType: List<AnimalType>) =
        animalTypeRepository.saveAnimalType(animalType)

    fun deleteAllAnimalType() = animalTypeRepository.deleteAllAnimalType()
}
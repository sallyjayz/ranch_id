package com.sallyjayz.ranchid.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.model.animaltype.AnimalType
import com.sallyjayz.ranchid.repository.AnimalTypeRepository
import javax.inject.Inject

class AnimalTypeViewModel @Inject constructor(
    private val animalTypeRepository: AnimalTypeRepository,
    context: Application
): AndroidViewModel(context) {

    private var _readAllAnimalType : LiveData<List<AnimalType>> = animalTypeRepository.readAll
    val readAllAnimalType: LiveData<List<AnimalType>>
        get() = _readAllAnimalType

    fun getAnimalTypeName(name: String) =
        animalTypeRepository.getAnimalTypeName(name)

    suspend fun insertAnimalType(animalType: List<AnimalType>) =
        animalTypeRepository.saveAnimalType(animalType)

    fun deleteAllAnimalType() = animalTypeRepository.deleteAllAnimalType()
}
package com.sallyjayz.ranchid.viewmodel.vet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.model.vet.livestocktypewithvaccine.AnimalTypeWithVaccine
import com.sallyjayz.ranchid.repository.vet.AnimalTypeWithVaccineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 29-Jun-24.
 */
@HiltViewModel
class AnimalTypeWithVaccineViewModel @Inject constructor(
    private val animalTypeWithVaccineRepository: AnimalTypeWithVaccineRepository,
    context: Application
): AndroidViewModel(context){

    /*private var _readAllAnimalTypeWithVaccine : LiveData<List<AnimalTypeWithVaccine>> =
        animalTypeWithVaccineRepository.readAll

    val readAllAnimalTypeWithVaccine : LiveData<List<AnimalTypeWithVaccine>>
        get() = _readAllAnimalTypeWithVaccine*/

    private var _readAllAnimalTypeWithVaccine : LiveData<List<String>> =
        animalTypeWithVaccineRepository.readAll

    val readAllAnimalTypeWithVaccine : LiveData<List<String>>
        get() = _readAllAnimalTypeWithVaccine

    fun getAnimalType(type: String) =
        animalTypeWithVaccineRepository.getAnimalType(type)

    fun getAnimalVaccineName(vaccine: String) =
        animalTypeWithVaccineRepository.getAnimalVaccineName(vaccine)

    suspend fun insertAnimalTypeWithVaccine(animalType: List<AnimalTypeWithVaccine>) =
        animalTypeWithVaccineRepository.saveAnimalTypeWithVaccine(animalType)

    fun deleteAllAnimalTypeWithVaccine() = animalTypeWithVaccineRepository.deleteAllAnimalTypeWithVaccine()

}
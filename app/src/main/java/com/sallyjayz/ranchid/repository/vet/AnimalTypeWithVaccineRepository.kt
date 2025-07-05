package com.sallyjayz.ranchid.repository.vet

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.VetAnimalTypeWithVaccineDao
import com.sallyjayz.ranchid.model.vet.livestocktypewithvaccine.AnimalTypeWithVaccine
import com.sallyjayz.ranchid.service.vet.AnimalTypeWithVaccineApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

/**
 * Created by Salama Jatau on 29-Jun-24.
 */
class AnimalTypeWithVaccineRepository @Inject constructor(
    private val animalTypeWithVaccineApiService: AnimalTypeWithVaccineApiService,
    private val vetAnimalTypeWithVaccineDao: VetAnimalTypeWithVaccineDao
) {

    fun getAnimalTypeWithVaccine() = apiRequestFlow {
        animalTypeWithVaccineApiService.getAllAnimalTypeWithVaccine()
    }

    /*val readAll: LiveData<List<AnimalTypeWithVaccine>> =
        vetAnimalTypeWithVaccineDao.readAllAnimalTypeWithVaccine()*/
    val readAll: LiveData<List<String>> =
        vetAnimalTypeWithVaccineDao.readAllAnimalTypeWithVaccine()

    suspend fun saveAnimalTypeWithVaccine(animalTypeWithVaccine: List<AnimalTypeWithVaccine>) =
        vetAnimalTypeWithVaccineDao.insertAnimalTypeWithVaccine(animalTypeWithVaccine)

    fun getAnimalType(type: String) =
        vetAnimalTypeWithVaccineDao.readSelectedAnimalType(type)

    fun getAnimalVaccineName(vaccine: String) =
        vetAnimalTypeWithVaccineDao.readSelectedAnimalVaccineName(vaccine)

    fun deleteAllAnimalTypeWithVaccine() =
        vetAnimalTypeWithVaccineDao.deleteAllAnimalTypeWithVaccine()

}
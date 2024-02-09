package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.AnimalTypeDao
import com.sallyjayz.ranchid.model.animaltype.AnimalType
import com.sallyjayz.ranchid.service.register.animaltypebreed.AnimalTypeBreedApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class AnimalTypeRepository @Inject constructor(
    private val animalTypeBreedApiService: AnimalTypeBreedApiService,
    private val animalTypeDao: AnimalTypeDao
)  {

    fun getAnimalType() = apiRequestFlow {
        animalTypeBreedApiService.getAllAnimalType()
    }

    val readAll: LiveData<List<AnimalType>> = animalTypeDao.readAllAnimalType()

    suspend fun saveAnimalType(animalType: List<AnimalType>) =
        animalTypeDao.insertAnimalType(animalType)

    fun getAnimalTypeName(name: String) =
        animalTypeDao.readSelectedAnimalTypeName(name)

    fun getAnimalTagType(type: String) =
        animalTypeDao.readSelectedAnimalTagType(type)

    fun deleteAllAnimalType() = animalTypeDao.deleteAllAnimalType()

}
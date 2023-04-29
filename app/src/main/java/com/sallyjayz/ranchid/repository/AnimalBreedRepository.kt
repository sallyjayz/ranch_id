package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.AnimalBreedDao
import com.sallyjayz.ranchid.model.animalbreed.AnimalBreed
import com.sallyjayz.ranchid.service.register.animaltypebreed.AnimalTypeBreedApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class AnimalBreedRepository @Inject constructor(
    private val animalTypeBreedApiService: AnimalTypeBreedApiService,
    private val animalBreedDao: AnimalBreedDao
)  {

    fun getAnimalBreed() = apiRequestFlow {
        animalTypeBreedApiService.getAllAnimalBreed()
    }

    val readAll: LiveData<List<AnimalBreed>> = animalBreedDao.readAllAnimalBreed()

    suspend fun saveAnimalBreed(animalBreed: List<AnimalBreed>) =
        animalBreedDao.insertAnimalBreed(animalBreed)

    fun getAnimalTypeBreedId(typeBreedId: Int) = animalBreedDao.readBreedById(typeBreedId)

    fun getAnimalBreedName(name: String) = animalBreedDao.readSelectedAnimalBreedName(name)

    fun deleteAllAnimalBreed() = animalBreedDao.deleteAllAnimalBreed()

}
package com.sallyjayz.ranchid.service.register.animaltypebreed

import com.sallyjayz.ranchid.model.animalbreed.AnimalBreedResponse
import com.sallyjayz.ranchid.model.animaltype.AnimalTypeResponse
import retrofit2.Response
import retrofit2.http.GET

interface AnimalTypeBreedApiService {

    @GET("/api/animal_types")
    suspend fun getAllAnimalType(): Response<AnimalTypeResponse>

    @GET("/api/animal_types/all")
    suspend fun getAllAnimalBreed(): Response<AnimalBreedResponse>

}
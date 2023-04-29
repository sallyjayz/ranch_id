package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.animalbreed.AnimalBreed

@Dao
interface AnimalBreedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimalBreed(animalBreed: List<AnimalBreed>)

    @Query("SELECT * FROM animal_breed")
    fun readAllAnimalBreed(): LiveData<List<AnimalBreed>>

    @Query("SELECT * FROM animal_breed WHERE name = :selectedAnimalBreedName")
    fun readSelectedAnimalBreedName(selectedAnimalBreedName: String): LiveData<AnimalBreed>

    @Query("SELECT * FROM animal_breed WHERE animal_type_id = :id")
    fun readBreedById(id: Int): LiveData<List<AnimalBreed>>

    @Query("DELETE FROM animal_breed")
    fun deleteAllAnimalBreed()

}
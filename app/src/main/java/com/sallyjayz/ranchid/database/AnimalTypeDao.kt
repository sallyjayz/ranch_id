package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.animaltype.AnimalType

@Dao
interface AnimalTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimalType(animalType: List<AnimalType>)

    @Query("SELECT * FROM animal_type")
    fun readAllAnimalType(): LiveData<List<AnimalType>>

    @Query("SELECT * FROM animal_type WHERE name = :selectedAnimalTypeName")
    fun readSelectedAnimalTypeName(selectedAnimalTypeName: String): LiveData<AnimalType>

    @Query("SELECT * FROM animal_type WHERE type = :selectedAnimalTagType")
    fun readSelectedAnimalTagType(selectedAnimalTagType: String): LiveData<AnimalType>

    @Query("DELETE FROM animal_type")
    fun deleteAllAnimalType()

}
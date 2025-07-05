package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.vet.livestocktypewithvaccine.AnimalTypeWithVaccine

/**
 * Created by Salama Jatau on 29-Jun-24.
 */
@Dao
interface VetAnimalTypeWithVaccineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimalTypeWithVaccine(animalTypeWithVaccine: List<AnimalTypeWithVaccine>)

    /*@Query("SELECT * FROM animal_type_with_vaccine")
    fun readAllAnimalTypeWithVaccine(): LiveData<List<AnimalTypeWithVaccine>>*/

    @Query("SELECT DISTINCT animalType FROM animal_type_with_vaccine")
    fun readAllAnimalTypeWithVaccine(): LiveData<List<String>>

    @Query("SELECT * FROM animal_type_with_vaccine WHERE animalType = :selectedAnimalType")
    fun readSelectedAnimalType(selectedAnimalType: String): LiveData<List<AnimalTypeWithVaccine>>

    @Query("SELECT * FROM animal_type_with_vaccine WHERE vaccineName = :selectedAnimalVaccineName")
    fun readSelectedAnimalVaccineName(selectedAnimalVaccineName: String): LiveData<AnimalTypeWithVaccine>

    @Query("DELETE FROM animal_type_with_vaccine")
    fun deleteAllAnimalTypeWithVaccine()

}
package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.vet.treatmenttypes.TreatmentType

/**
 * Created by Salama Jatau on 05-Jul-24.
 */
@Dao
interface VetTreatmentTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTreatmentTypes(treatmentType: List<TreatmentType>)

    @Query("SELECT DISTINCT type FROM treatment_type")
    fun readAllTreatmentType(): LiveData<List<String>>

    @Query("SELECT * FROM treatment_type WHERE type = :selectedCategory")
    fun readSelectedTreatmentCategory(selectedCategory: String): LiveData<List<TreatmentType>>

    @Query("SELECT * FROM treatment_type WHERE variants = :selectedType")
    fun readSelectedTreatmentType(selectedType: String): LiveData<TreatmentType>

    @Query("DELETE FROM treatment_type")
    fun deleteAllTreatmentType()

}
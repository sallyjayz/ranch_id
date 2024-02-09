package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.farmlocation.FarmLocation

@Dao
interface FarmLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(farmLocation: List<FarmLocation>)

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(farmLocation: FarmLocation)*/

    @Query("SELECT * FROM FarmLocation")
    fun getAllLocation(): LiveData<List<FarmLocation>>

    @Query("SELECT * FROM FarmLocation WHERE location_name = :selectedStateName")
    fun getSelectedLocationName(selectedStateName: String): LiveData<FarmLocation>

    @Query("SELECT * FROM FarmLocation WHERE id = :id")
    fun getSelectedLocationId(id: Int): LiveData<FarmLocation>

    @Query("DELETE FROM FarmLocation")
    fun deleteAllFarmLocation()
}
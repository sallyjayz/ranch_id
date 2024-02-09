package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.lga.LGA

@Dao
interface LgaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLGAs(lgas: List<LGA>)

    @Query("SELECT * FROM lga")
    fun readAllLGA(): LiveData<List<LGA>>

    @Query("SELECT * FROM lga WHERE state_id = :id")
    fun readLGA(id: Int): LiveData<List<LGA>>

    @Query("SELECT * FROM lga WHERE name = :selectedLgaName")
    fun readSelectedLgaName(selectedLgaName: String): LiveData<LGA>

    @Query("SELECT * FROM lga WHERE id = :id AND state_id = :state_id")
    fun readSelectedLgaAndStateId(id: Int, state_id: Int): LiveData<LGA>

    @Query("DELETE FROM lga")
    fun deleteAllLGA()
}
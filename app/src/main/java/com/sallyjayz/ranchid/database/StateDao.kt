package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.state.States

@Dao
interface StateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStates(states: List<States>)

    @Query("SELECT * FROM states")
    fun readAllState(): LiveData<List<States>>

    @Query("SELECT * FROM states WHERE name = :selectedStateName")
    fun readSelectedStateName(selectedStateName: String): LiveData<States>

    @Query("DELETE FROM states")
    fun deleteAllStates()

}
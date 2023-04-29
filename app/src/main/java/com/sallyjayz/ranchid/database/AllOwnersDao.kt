package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.allowners.AllOwners

@Dao
interface AllOwnersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllOwners(allOwners: List<AllOwners>)

    @Query("SELECT * FROM allowners ORDER BY surname")
    fun readAllOwners(): LiveData<List<AllOwners>>

    @Query("SELECT * FROM allowners WHERE id = :id")
    fun readOwnerById(id: Int): LiveData<List<AllOwners>>

    @Query("SELECT * FROM allowners WHERE surname = :surname AND other_names = :othername")
    fun readSelectedOwnerName(surname: String, othername: String): LiveData<AllOwners>

    @Query("DELETE FROM allowners")
    fun deleteAllOwners()

}
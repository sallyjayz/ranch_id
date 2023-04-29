package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.allkeepers.AllKeepers

@Dao
interface AllKeepersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeepers(allKeepers: List<AllKeepers>)

    @Query("SELECT * FROM allkeepers ORDER BY surname")
    fun readAllKeepers(): LiveData<List<AllKeepers>>

    @Query("SELECT * FROM allkeepers WHERE id = :id")
    fun readKeeperById(id: Int): LiveData<List<AllKeepers>>

    @Query("SELECT * FROM allkeepers WHERE surname = :surname AND other_names = :othername")
    fun readSelectedKeeperName(surname: String, othername: String): LiveData<AllKeepers>

    @Query("DELETE FROM allkeepers")
    fun deleteAllKeepers()

}
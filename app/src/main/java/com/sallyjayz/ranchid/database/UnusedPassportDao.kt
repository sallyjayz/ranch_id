package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.unusedpassport.UnusedPassport

@Dao
interface UnusedPassportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnusedPassport(unusedPassport: UnusedPassport)

    @Query("SELECT * FROM unused_passport")
    fun getAllUnusedPassport(): LiveData<List<UnusedPassport>>

    @Query("SELECT * FROM unused_passport WHERE passportId = :selectedPassportId")
    fun getSelectedUnusedPassport(selectedPassportId: String): LiveData<UnusedPassport>

    @Query("DELETE FROM unused_passport")
    fun deleteAllUnusedPassport()
}
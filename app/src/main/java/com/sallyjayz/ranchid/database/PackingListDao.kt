package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.register.packinglist.packinglist.AllPackingList
/**
 * Created by Salama Jatau on 01-Jul-23.
 */

@Dao
interface PackingListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPackingList(allPackingList: AllPackingList)

    @Query("DELETE FROM packing_list")
    fun deletePackingList()

    @Query("SELECT * FROM packing_list")
    fun readAllPackingList(): LiveData<List<AllPackingList>>

    @Query("SELECT * FROM packing_list WHERE tags = :tagId")
    fun getSelectedPackingList(tagId: String): LiveData<AllPackingList>
}
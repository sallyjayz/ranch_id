package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.sallyjayz.ranchid.model.register.packinglist.scannedlivestock.ScanLivestock

/**
 * Created by Salama Jatau on 23-Jun-23.
 */

@Dao
interface LivestockDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLivestockData(scanLivestock: ScanLivestock)

    /*@Query("SELECT * FROM livestock_data WHERE tag_id = :tagId")
    fun getSelectedLivestockData(tagId: String): LiveData<ScanLivestock>*/

    @Query("DELETE FROM livestock_data")
    fun deleteLivestockData()

    @Query("SELECT * FROM livestock_data")
    fun getAllLivestockData(): PagingSource<Int, ScanLivestock>

    @Query("SELECT * FROM livestock_data")
    fun readAllLivestockData(): LiveData<List<ScanLivestock>>

}
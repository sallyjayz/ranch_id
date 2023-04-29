package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.sallyjayz.ranchid.model.offline.taglivestock.OfflineTagLivestock

@Dao
interface OfflineTagLivestockDao {

    @Query("SELECT * FROM tag_livestock")
    fun getAllOfflineTagLivestock(): PagingSource<Int, OfflineTagLivestock>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOfflineTagLivestock(offlineTagLivestock: OfflineTagLivestock)

    @Query("SELECT * FROM tag_livestock")
    fun readAllOfflineTagLivestock(): LiveData<List<OfflineTagLivestock>>

    @Query("UPDATE tag_livestock SET status = :status WHERE id = :id")
    fun updateOfflineTagLivestockWithId(id: Int, status: String): Int

    @Query("UPDATE tag_livestock SET tagId = :tagId, status = :status WHERE id = :id")
    fun updateOfflineTagLivestockWithTag(id: Int, tagId: String, status: String): Int

    @Query("DELETE FROM tag_livestock WHERE status = :status")
    fun deleteOfflineTagLivestockStatusCondition(status: String): Int

    @Update
    suspend fun updateOfflineTagLivestock(offlineTagLivestock: OfflineTagLivestock)

    @Delete
    suspend fun deleteOfflineTagLivestock(offlineTagLivestock: OfflineTagLivestock)

}
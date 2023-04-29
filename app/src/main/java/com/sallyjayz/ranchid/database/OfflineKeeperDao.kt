package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.sallyjayz.ranchid.model.offline.keeper.OfflineKeeper

@Dao
interface OfflineKeeperDao {

    @Query("SELECT * FROM offline_keeper")
    fun getAllOfflineKeeper(): PagingSource<Int, OfflineKeeper>

    @Query("SELECT * FROM offline_keeper")
    fun readAllOfflineKeepers(): LiveData<List<OfflineKeeper>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOfflineKeeper(offlineKeeper: OfflineKeeper)

    @Query("UPDATE offline_keeper SET status = :status WHERE id = :id")
    fun updateOfflineKeeperWithId(status: String, id: Int): Int
//    fun updateOfflineKeeperWithId(id: Int, status: Boolean?): Int

    @Query("UPDATE offline_keeper SET phoneNumber = :phoneNumber, nin = :nin, " +
            "emailAddress = :emailAddress, nextOfKinPhoneNumber = :nextOfKinPhoneNumber, " +
            "status = :status WHERE id = :id")
    fun updateOfflineKeeperData(id: Int, phoneNumber: String, nin: String, emailAddress: String,
                                  nextOfKinPhoneNumber: String, status: String): Int

    @Query("DELETE FROM offline_keeper WHERE status = :status")
    fun deleteOfflineKeeperStatusCondition(status: String): Int

    @Update
    suspend fun updateOfflineKeeper(offlineKeeper: OfflineKeeper)

    @Delete
    suspend fun deleteOfflineKeeper(offlineKeeper: OfflineKeeper)

}
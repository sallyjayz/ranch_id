package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.sallyjayz.ranchid.model.offline.owner.OfflineOwner

@Dao
interface OfflineOwnerDao {

    @Query("SELECT * FROM offline_owner")
    fun getAllOfflineOwner(): PagingSource<Int, OfflineOwner>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOfflineOwner(offlineOwner: OfflineOwner)

    @Query("SELECT * FROM offline_owner")
    fun readAllOfflineOwners(): LiveData<List<OfflineOwner>>

    @Query("UPDATE offline_owner SET status = :status WHERE id = :id")
    fun updateOfflineOwnerWithId(id: Int, status: String): Int

    @Query("UPDATE offline_owner SET phoneNumber = :phoneNumber, nin = :nin, " +
            "emailAddress = :emailAddress, nextOfKinPhoneNumber = :nextOfKinPhoneNumber, " +
            "status = :status WHERE id = :id")
    fun updateOfflineOwnerWithData(id: Int, phoneNumber: String, nin: String, emailAddress: String,
                                 nextOfKinPhoneNumber: String, status: String): Int

    @Query("DELETE FROM offline_owner WHERE status = :status")
    fun deleteOfflineOwnerStatusCondition(status: String): Int

    @Update
    suspend fun updateOfflineOwner(offlineOwner: OfflineOwner)

    @Delete
    suspend fun deleteOfflineOwner(offlineOwner: OfflineOwner)

}
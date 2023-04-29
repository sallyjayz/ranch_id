package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.sallyjayz.ranchid.database.OfflineOwnerDao
import com.sallyjayz.ranchid.model.offline.owner.OfflineOwner
import javax.inject.Inject

class OfflineOwnerRepository @Inject constructor(
    private val offlineOwnerDao: OfflineOwnerDao
) {

    fun readAllOfflineOwners(): PagingSource<Int, OfflineOwner> {
        return offlineOwnerDao.getAllOfflineOwner()
    }

    suspend fun insertOfflineOwner(offlineOwner: OfflineOwner) {
        offlineOwnerDao.insertOfflineOwner(offlineOwner)
    }

    fun selectAllOfflineOwners(): LiveData<List<OfflineOwner>> {
        return offlineOwnerDao.readAllOfflineOwners()
    }

    fun updateOfflineOwner(id: Int, status: String): Int {
        return offlineOwnerDao.updateOfflineOwnerWithId(id, status)
    }

    fun updateOfflineOwnerData(id: Int, phoneNumber: String, nin: String, emailAddress: String,
                                nextOfKinPhoneNumber: String, status: String): Int {
        return offlineOwnerDao.updateOfflineOwnerWithData(id, phoneNumber, nin, emailAddress,
            nextOfKinPhoneNumber, status)
    }

    fun deleteOfflineOwnerStatusCondition(status: String): Int {
        return offlineOwnerDao.deleteOfflineOwnerStatusCondition(status)
    }

}
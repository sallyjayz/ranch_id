package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.sallyjayz.ranchid.database.OfflineKeeperDao
import com.sallyjayz.ranchid.model.offline.keeper.OfflineKeeper
import javax.inject.Inject

class OfflineKeeperRepository @Inject constructor(
    private val offlineKeeperDao: OfflineKeeperDao
) {

    fun readAllOfflineKeepers(): PagingSource<Int, OfflineKeeper> {
        return offlineKeeperDao.getAllOfflineKeeper()
    }

    suspend fun insertOfflineKeeper(offlineKeeper: OfflineKeeper) {
        offlineKeeperDao.insertOfflineKeeper(offlineKeeper)
    }

    fun selectAllOfflineKeepers(): LiveData<List<OfflineKeeper>> {
        return offlineKeeperDao.readAllOfflineKeepers()
    }

    /*fun updateOfflineKeeper(id: Int, status: Boolean?): Int {
        return offlineKeeperDao.updateOfflineKeeperWithId(id, status)
    }*/

    fun updateOfflineKeeper(status: String, id: Int): Int {
        return offlineKeeperDao.updateOfflineKeeperWithId(status, id)
    }

    fun updateOfflineKeeperData(id: Int, phoneNumber: String, nin: String, emailAddress: String,
                            nextOfKinPhoneNumber: String, status: String): Int {
        return offlineKeeperDao.updateOfflineKeeperData(id, phoneNumber, nin, emailAddress,
            nextOfKinPhoneNumber, status)
    }

    fun deleteOfflineKeeperStatusCondition(status: String): Int {
        return offlineKeeperDao.deleteOfflineKeeperStatusCondition(status)
    }
}
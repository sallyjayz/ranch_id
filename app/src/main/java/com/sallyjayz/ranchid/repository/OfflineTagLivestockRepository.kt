package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.sallyjayz.ranchid.database.OfflineTagLivestockDao
import com.sallyjayz.ranchid.model.offline.taglivestock.OfflineTagLivestock
import javax.inject.Inject

class OfflineTagLivestockRepository @Inject constructor(
    private val offlineTagLivestockDao: OfflineTagLivestockDao
) {

    fun readAllOfflineTagLivestocks(): PagingSource<Int, OfflineTagLivestock> {
        return offlineTagLivestockDao.getAllOfflineTagLivestock()
    }

    suspend fun insertOfflineTagLivestock(offlineTagLivestock: OfflineTagLivestock) {
        offlineTagLivestockDao.insertOfflineTagLivestock(offlineTagLivestock)
    }

    fun selectAllOfflineTagLivestock(): LiveData<List<OfflineTagLivestock>> {
        return offlineTagLivestockDao.readAllOfflineTagLivestock()
    }

    fun updateOfflineTagLivestock(id: Int, status: String): Int {
        return offlineTagLivestockDao.updateOfflineTagLivestockWithId(id, status)
    }

    fun updateOfflineTagLivestockTag(id: Int, tagId:String, status: String): Int {
        return offlineTagLivestockDao.updateOfflineTagLivestockWithTag(id, tagId, status)
    }

    fun deleteOfflineTagLivestockStatusCondition(status: String): Int {
        return offlineTagLivestockDao.deleteOfflineTagLivestockStatusCondition(status)
    }
}
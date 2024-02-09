package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.sallyjayz.ranchid.database.LivestockDataDao
import com.sallyjayz.ranchid.database.PackingListDao
import com.sallyjayz.ranchid.model.register.packinglist.packinglist.AllPackingList
import com.sallyjayz.ranchid.model.register.packinglist.postpackinglist.PackingListPost
import com.sallyjayz.ranchid.model.register.packinglist.scannedlivestock.ScanLivestock
import com.sallyjayz.ranchid.service.register.packinglist.PackingListApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

/**
 * Created by Salama Jatau on 18-Jun-23.
 */
class PackingListRepository @Inject constructor(
    private val packingListApiService: PackingListApiService,
    private val livestockDataDao: LivestockDataDao,
    private val packingListDao: PackingListDao
) {

    fun getPackingListTagDetail(tagId: String) = apiRequestFlow {
        packingListApiService.getPackingListScannedTagDetail(tagId)
    }

    fun getPackingList(username: String) = apiRequestFlow {
        packingListApiService.getPackingList(username)
    }

    fun packingListPost(username: String, packingListPost: PackingListPost) = apiRequestFlow {
        packingListApiService.packingListPost(username, packingListPost)
    }

    suspend fun insertLivestockData(scanLivestock: ScanLivestock) {
        livestockDataDao.insertLivestockData(scanLivestock)
    }

    /*fun getSelectedLivestockData(selectedLivestockData: String) =
        livestockDataDao.getSelectedLivestockData(selectedLivestockData)*/

    fun readAllLivestockData(): PagingSource<Int, ScanLivestock> {
        return livestockDataDao.getAllLivestockData()
    }


    fun selectAllLivestockData(): LiveData<List<ScanLivestock>> {
        return livestockDataDao.readAllLivestockData()
    }

    fun deleteLivestockData() = livestockDataDao.deleteLivestockData()

    suspend fun insertPackingList(packingList: AllPackingList) {
        packingListDao.insertPackingList(packingList)
    }

    fun readAllPackingList(): LiveData<List<AllPackingList>> {
        return packingListDao.readAllPackingList()
    }

    fun getSelectedPackingList(selectedPackingList: String) =
        packingListDao.getSelectedPackingList(selectedPackingList)

    fun deletePackingList() = packingListDao.deletePackingList()

}
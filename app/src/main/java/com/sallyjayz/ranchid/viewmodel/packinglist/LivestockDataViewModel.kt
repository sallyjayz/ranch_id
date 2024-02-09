package com.sallyjayz.ranchid.viewmodel.packinglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sallyjayz.ranchid.model.register.packinglist.packinglist.AllPackingList
import com.sallyjayz.ranchid.model.register.packinglist.scannedlivestock.ScanLivestock
import com.sallyjayz.ranchid.repository.PackingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Salama Jatau on 23-Jun-23.
 */

@HiltViewModel
class LivestockDataViewModel @Inject constructor(
    private val packingListRepository: PackingListRepository,
    context: Application
): AndroidViewModel(context) {

    suspend fun insertLivestockData(scanLivestock: ScanLivestock) =
        packingListRepository.insertLivestockData(scanLivestock)

    /*fun getSelectedLivestockData(selectedLivestockData: String) =
        packingListRepository.getSelectedLivestockData(selectedLivestockData)*/

    fun readLivestockData(): Flow<PagingData<ScanLivestock>> {
        return Pager(config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = {
                packingListRepository.readAllLivestockData()
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun selectAllLivestockData() : LiveData<List<ScanLivestock>> {
        return packingListRepository.selectAllLivestockData()
    }

    fun deleteLivestockData() = packingListRepository.deleteLivestockData()

    suspend fun insertPackingList(allPackingList: AllPackingList) =
        packingListRepository.insertPackingList(allPackingList)

    fun selectPackingList(): LiveData<List<AllPackingList>> {
        return packingListRepository.readAllPackingList()
    }

    fun getSelectedPackingList(selectedPackingList: String) =
        packingListRepository.getSelectedPackingList(selectedPackingList)

    fun deletePackingList() = packingListRepository.deletePackingList()

}
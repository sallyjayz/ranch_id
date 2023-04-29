package com.sallyjayz.ranchid.viewmodel.offline

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sallyjayz.ranchid.model.offline.taglivestock.OfflineTagLivestock
import com.sallyjayz.ranchid.repository.OfflineTagLivestockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class OfflineTagLivestockViewModel @Inject constructor(
    private val offlineTagLivestockRepository: OfflineTagLivestockRepository,
    context: Application
): AndroidViewModel(context) {

    fun readOfflineTagLivestock(): Flow<PagingData<OfflineTagLivestock>> {
        return Pager(config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = {
                offlineTagLivestockRepository.readAllOfflineTagLivestocks()
            }).flow.cachedIn(viewModelScope)
    }

    suspend fun insertOfflineTagLivestock(offlineTagLivestock: OfflineTagLivestock) =
        offlineTagLivestockRepository.insertOfflineTagLivestock(offlineTagLivestock)

    fun selectAllOfflineTagLivestock() : LiveData<List<OfflineTagLivestock>> {
        return offlineTagLivestockRepository.selectAllOfflineTagLivestock()
    }

    fun updateOfflineTagLivestock(id: Int, status: String): Int {
        return offlineTagLivestockRepository.updateOfflineTagLivestock(id, status)
    }

    fun updateOfflineTagLivestockTag(id: Int, tagId: String, status: String): Int {
        return offlineTagLivestockRepository.updateOfflineTagLivestockTag(id, tagId, status)
    }

    fun deleteOfflineTagLivestockStatusCondition(status: String): Int {
        return offlineTagLivestockRepository.deleteOfflineTagLivestockStatusCondition(status)
    }
}
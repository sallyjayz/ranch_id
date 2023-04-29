package com.sallyjayz.ranchid.viewmodel.offline

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sallyjayz.ranchid.model.offline.keeper.OfflineKeeper
import com.sallyjayz.ranchid.repository.OfflineKeeperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class OfflineKeeperViewModel @Inject constructor(
    private val offlineKeeperRepository: OfflineKeeperRepository,
    context: Application
): AndroidViewModel(context) {

    fun readOfflineKeepers(): Flow<PagingData<OfflineKeeper>> {
        return Pager(config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = {
                offlineKeeperRepository.readAllOfflineKeepers()
            }
        ).flow.cachedIn(viewModelScope)
    }

    suspend fun insertOfflineKeeper(offlineKeeper: OfflineKeeper) =
        offlineKeeperRepository.insertOfflineKeeper(offlineKeeper)

    fun selectAllOfflineKeepers() : LiveData<List<OfflineKeeper>> {
        return offlineKeeperRepository.selectAllOfflineKeepers()
    }

    /*fun updateOfflineKeeper(id: Int, status: Boolean?): Int {
        return offlineKeeperRepository.updateOfflineKeeper(id, status)
    }*/

    fun updateOfflineKeeper(status: String, id: Int): Int {
        return offlineKeeperRepository.updateOfflineKeeper(status, id)
    }

    fun updateOfflineKeeperData(id: Int, phoneNumber: String, nin: String, emailAddress: String,
                            nextOfKinPhoneNumber: String, status: String): Int {
        return offlineKeeperRepository.updateOfflineKeeperData(id, phoneNumber, nin, emailAddress,
            nextOfKinPhoneNumber, status)
    }

    fun deleteOfflineKeeperStatusCondition(status: String): Int {
        return offlineKeeperRepository.deleteOfflineKeeperStatusCondition(status)
    }
}
package com.sallyjayz.ranchid.viewmodel.offline

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sallyjayz.ranchid.model.offline.owner.OfflineOwner
import com.sallyjayz.ranchid.repository.OfflineOwnerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class OfflineOwnerViewModel @Inject constructor(
    private val offlineOwnerRepository: OfflineOwnerRepository,
    context: Application
): AndroidViewModel(context) {

    fun readOfflineOwners(): Flow<PagingData<OfflineOwner>> {
        return Pager(config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = {
                offlineOwnerRepository.readAllOfflineOwners()
            }).flow.cachedIn(viewModelScope)
    }

    suspend fun insertOfflineOwner(offlineOwner: OfflineOwner) =
        offlineOwnerRepository.insertOfflineOwner(offlineOwner)

    fun selectAllOfflineOwners() : LiveData<List<OfflineOwner>> {
        return offlineOwnerRepository.selectAllOfflineOwners()
    }

    fun updateOfflineOwner(id: Int, status: String): Int {
        return offlineOwnerRepository.updateOfflineOwner(id, status)
    }

    fun updateOfflineOwnerData(id: Int, phoneNumber: String, nin: String, emailAddress: String,
                                nextOfKinPhoneNumber: String, status: String): Int {
        return offlineOwnerRepository.updateOfflineOwnerData(id, phoneNumber, nin, emailAddress,
            nextOfKinPhoneNumber, status)
    }

    fun deleteOfflineOwnerStatusCondition(status: String): Int {
        return offlineOwnerRepository.deleteOfflineOwnerStatusCondition(status)
    }

}
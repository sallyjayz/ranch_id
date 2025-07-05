package com.sallyjayz.ranchid.viewmodel.report

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.allkeepers.AllKeepersResponse
import com.sallyjayz.ranchid.model.allowners.AllOwnersResponse
import com.sallyjayz.ranchid.repository.ReportListRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 28-Jan-25.
 */

@HiltViewModel
class ReportResponseViewModel @Inject constructor(
    private val reportListRepository: ReportListRepository
): BaseViewModel() {

    private val _allOwnerResponse = MutableLiveData<ApiResponse<AllOwnersResponse>>()
    val allOwnerResponse = _allOwnerResponse

    private val _allKeeperResponse = MutableLiveData<ApiResponse<AllKeepersResponse>>()
    val allKeeperResponse = _allKeeperResponse

    fun getAllKeeper(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _allKeeperResponse,
        coroutinesErrorHandler
    ) {
        reportListRepository.getAllKeepers()
    }

    fun getAllOwner(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _allOwnerResponse,
        coroutinesErrorHandler
    ) {
        reportListRepository.getAllOwners()
    }

}
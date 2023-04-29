package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.allkeepers.AllKeepersResponse
import com.sallyjayz.ranchid.repository.AllKeeperListRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllKeeperResponseViewModel @Inject constructor(
    private val allKeeperListRepository: AllKeeperListRepository
): BaseViewModel() {

    private val _allKeeperResponse = MutableLiveData<ApiResponse<AllKeepersResponse>>()
    val allKeeperResponse = _allKeeperResponse

    fun getAllKeeper(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _allKeeperResponse,
        coroutinesErrorHandler
    ) {
        allKeeperListRepository.getAllKeepers()
    }

}
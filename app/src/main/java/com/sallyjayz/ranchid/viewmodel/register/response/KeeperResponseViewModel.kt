package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.register.keeper.AddKeeperResponse
import com.sallyjayz.ranchid.model.register.keeper.Keeper
import com.sallyjayz.ranchid.repository.KeeperRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KeeperResponseViewModel @Inject constructor(
    private val keeperRepository: KeeperRepository
): BaseViewModel()  {

    private val _addKeeperResponse = MutableLiveData<ApiResponse<AddKeeperResponse>>()
    val addKeeperResponse = _addKeeperResponse



    fun addKeeper(keeper: Keeper, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _addKeeperResponse,
        coroutinesErrorHandler
    ) {
        keeperRepository.addKeeper(keeper)
    }

}
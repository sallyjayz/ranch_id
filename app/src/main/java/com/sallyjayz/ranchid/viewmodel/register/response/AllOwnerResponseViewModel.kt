package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.allowners.AllOwnersResponse
import com.sallyjayz.ranchid.repository.AllOwnerListRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllOwnerResponseViewModel @Inject constructor(
    private val allOwnerListRepository: AllOwnerListRepository
): BaseViewModel() {

    private val _allOwnerResponse = MutableLiveData<ApiResponse<AllOwnersResponse>>()
    val allOwnerResponse = _allOwnerResponse

    fun getAllOwner(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _allOwnerResponse,
        coroutinesErrorHandler
    ) {
        allOwnerListRepository.getAllOwners()
    }

}
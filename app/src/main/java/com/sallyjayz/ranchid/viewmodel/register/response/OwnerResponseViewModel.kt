package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.register.owner.AddOwnerResponse
import com.sallyjayz.ranchid.model.register.owner.Owner
import com.sallyjayz.ranchid.repository.OwnerRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OwnerResponseViewModel @Inject constructor(
    private val ownerRepository: OwnerRepository
): BaseViewModel() {

    private val _addOwnerResponse = MutableLiveData<ApiResponse<AddOwnerResponse>>()
    val addOwnerResponse = _addOwnerResponse



    fun addOwner(owner: Owner, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _addOwnerResponse,
        coroutinesErrorHandler
    ) {
        ownerRepository.addOwner(owner)
    }

}
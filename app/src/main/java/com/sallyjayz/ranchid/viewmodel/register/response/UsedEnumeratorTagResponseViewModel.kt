package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.usedenumeratortag.UsedEnumeratorTagResponse
import com.sallyjayz.ranchid.repository.UsedEnumeratorTagRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 20-Jun-23.
 */

@HiltViewModel
class UsedEnumeratorTagResponseViewModel @Inject constructor(
    private val usedEnumeratorTagRepository: UsedEnumeratorTagRepository
): BaseViewModel() {

    private val _allUsedEnumeratorTagResponse = MutableLiveData<ApiResponse<UsedEnumeratorTagResponse>>()
    val allUsedEnumeratorTagResponse = _allUsedEnumeratorTagResponse

    fun getAllUsedEnumeratorTag(username: String, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _allUsedEnumeratorTagResponse,
        coroutinesErrorHandler
    ) {
        usedEnumeratorTagRepository.getAllUsedEnumeratorTag(username)
    }

}
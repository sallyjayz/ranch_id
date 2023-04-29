package com.sallyjayz.ranchid.viewmodel.offline.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.unusedenumeratortag.UnusedEnumeratorTagResponse
import com.sallyjayz.ranchid.repository.UnusedEnumeratorTagRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnusedEnumeratorTagResponseViewModel @Inject constructor(
    private val unusedEnumeratorTagRepository: UnusedEnumeratorTagRepository
): BaseViewModel() {

    private val _unusedEnumeratorTagResponse = MutableLiveData<ApiResponse<UnusedEnumeratorTagResponse>>()
    val unusedEnumeratorTagResponse = _unusedEnumeratorTagResponse

    fun getUnusedEnumeratorTag(username: String, tagId: String, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _unusedEnumeratorTagResponse,
        coroutinesErrorHandler
    ) {
        unusedEnumeratorTagRepository.getUnusedEnumeratorTag(username, tagId)
    }

}
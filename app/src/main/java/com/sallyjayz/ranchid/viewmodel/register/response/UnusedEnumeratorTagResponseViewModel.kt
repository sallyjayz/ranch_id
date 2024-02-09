package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.unusedenumeratortag.all.AllUnusedEnumeratorTagResponse
import com.sallyjayz.ranchid.model.unusedenumeratortag.byId.UnusedEnumeratorTagResponseById
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

    private val _unusedEnumeratorTagResponseById = MutableLiveData<ApiResponse<UnusedEnumeratorTagResponseById>>()
    val unusedEnumeratorTagResponse = _unusedEnumeratorTagResponseById

    private val _allUnusedEnumeratorTagResponse = MutableLiveData<ApiResponse<AllUnusedEnumeratorTagResponse>>()
    val allUnusedEnumeratorTag = _allUnusedEnumeratorTagResponse

    fun getUnusedEnumeratorTagById(username: String, tagId: String, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _unusedEnumeratorTagResponseById,
        coroutinesErrorHandler
    ) {
        unusedEnumeratorTagRepository.getUnusedEnumeratorTagById(username, tagId)
    }

    fun getAllUnusedEnumeratorTag(username: String, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _allUnusedEnumeratorTagResponse,
        coroutinesErrorHandler
    ) {
        unusedEnumeratorTagRepository.getAllUnusedEnumeratorTag(username)
    }

}
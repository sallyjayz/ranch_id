package com.sallyjayz.ranchid.viewmodel.register.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.register.taglivestock.TagLivestock
import com.sallyjayz.ranchid.model.register.taglivestock.TagLivestockResponse
import com.sallyjayz.ranchid.repository.TagLivestockRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TagLivestockResponseViewModel @Inject constructor(
    private val tagLivestockRepository: TagLivestockRepository
): BaseViewModel() {

    private val _tagLivestockResponse = MutableLiveData<ApiResponse<TagLivestockResponse>>()
    val tagLivestockResponse = _tagLivestockResponse

    fun addTagLivestock(tagLivestock: TagLivestock, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _tagLivestockResponse,
        coroutinesErrorHandler
    ) {
        tagLivestockRepository.addTagLivestock(tagLivestock)
    }
}
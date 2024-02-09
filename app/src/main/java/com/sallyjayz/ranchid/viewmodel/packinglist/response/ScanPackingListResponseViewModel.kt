package com.sallyjayz.ranchid.viewmodel.packinglist.response

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.register.packinglist.packinglist.PackingListResponse
import com.sallyjayz.ranchid.model.register.packinglist.postpackinglist.PackingListPost
import com.sallyjayz.ranchid.model.register.packinglist.postpackinglist.PackingListPostResponse
import com.sallyjayz.ranchid.model.register.packinglist.scannedlivestock.ScanLivestockResponse
import com.sallyjayz.ranchid.repository.PackingListRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Created by Salama Jatau on 18-Jun-23.
 */

@HiltViewModel
class ScanPackingListResponseViewModel @Inject constructor(
    private val packingListRepository: PackingListRepository
): BaseViewModel()  {

    private val _scanTagDetailResponse = MutableLiveData<ApiResponse<ScanLivestockResponse>>()
    val scanTagDetailResponse = _scanTagDetailResponse

    private val _packingListResponse = MutableLiveData<ApiResponse<PackingListResponse>>()
    val packingListResponse = _packingListResponse

    private val _packingListPostResponse = MutableLiveData<ApiResponse<PackingListPostResponse>>()
    val packingListPostResponse = _packingListPostResponse

    fun scanTagDetail(tagId: String, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _scanTagDetailResponse,
        coroutinesErrorHandler
    ) {
        packingListRepository.getPackingListTagDetail(tagId)
    }

    fun getParkingList(username: String, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _packingListResponse,
        coroutinesErrorHandler
    ) {
        packingListRepository.getPackingList(username)
    }

    fun parkingListPost(username: String, packingListPost: PackingListPost, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _packingListPostResponse,
        coroutinesErrorHandler
    ) {
        packingListRepository.packingListPost(username, packingListPost)
    }

}
package com.sallyjayz.ranchid.viewmodel.generalinformation

import androidx.lifecycle.MutableLiveData
import com.sallyjayz.ranchid.model.generalInformation.generateinformation.request.GenerateInformationPost
import com.sallyjayz.ranchid.model.generalInformation.generateinformation.response.GenerateInformationResponse
import com.sallyjayz.ranchid.model.generalInformation.search.GeneralInfoSearchResponse
import com.sallyjayz.ranchid.repository.GeneralInformationRepository
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 05-Jul-23.
 */

@HiltViewModel
class GeneralInformationResponseViewModel @Inject constructor(
    private val generalInformationRepository: GeneralInformationRepository
): BaseViewModel() {

    private val _searchNameResponse = MutableLiveData<ApiResponse<GeneralInfoSearchResponse>>()
    val searchNameResponse = _searchNameResponse

    private val _generateInformationResponse = MutableLiveData<ApiResponse<GenerateInformationResponse>>()
    val generateInformationResponse = _generateInformationResponse

    fun getSearchName(searchName: String, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _searchNameResponse,
        coroutinesErrorHandler
    ) {
        generalInformationRepository.getSearchName(searchName)
    }

    fun generateInformationPost(generateInformationPost: GenerateInformationPost, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _generateInformationResponse,
        coroutinesErrorHandler
    ) {
        generalInformationRepository.generateInformationPost(generateInformationPost)
    }

}
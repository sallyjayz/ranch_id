package com.sallyjayz.ranchid.repository

import com.sallyjayz.ranchid.model.generalInformation.generateinformation.request.GenerateInformationPost
import com.sallyjayz.ranchid.service.generalinformation.GeneralInformationApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

/**
 * Created by Salama Jatau on 05-Jul-23.
 */
class GeneralInformationRepository @Inject constructor(
    private val generalInformationApiService: GeneralInformationApiService
){

    fun getSearchName(searchName: String) = apiRequestFlow {
        generalInformationApiService.getSearchName(searchName)
    }

    fun generateInformationPost(generateInformationPost: GenerateInformationPost) = apiRequestFlow {
        generalInformationApiService.generateInformationPost(generateInformationPost)
    }

}
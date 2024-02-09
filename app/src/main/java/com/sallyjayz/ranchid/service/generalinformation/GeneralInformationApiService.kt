package com.sallyjayz.ranchid.service.generalinformation

import com.sallyjayz.ranchid.model.generalInformation.generateinformation.request.GenerateInformationPost
import com.sallyjayz.ranchid.model.generalInformation.generateinformation.response.GenerateInformationResponse
import com.sallyjayz.ranchid.model.generalInformation.search.GeneralInfoSearchResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Salama Jatau on 05-Jul-23.
 */
interface GeneralInformationApiService {

    @GET("/api/custodian/search/{searchName}")
    suspend fun getSearchName(
        @Path("searchName") searchName: String
    ): Response<GeneralInfoSearchResponse>

    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @POST("/api/custodian/reports")
    suspend fun generateInformationPost(
        @Body generateInformationPost: GenerateInformationPost
    ): Response<GenerateInformationResponse>
}
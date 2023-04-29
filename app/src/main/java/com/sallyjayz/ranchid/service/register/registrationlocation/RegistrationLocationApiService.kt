package com.sallyjayz.ranchid.service.register.registrationlocation

import com.sallyjayz.ranchid.model.register.registrationlocation.RegistrationLocation
import com.sallyjayz.ranchid.model.register.registrationlocation.RegistrationLocationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegistrationLocationApiService {

    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @POST("/api/enumerator/add_registration_location/")
    suspend fun addRegistrationLocation(
        @Body registrationLocation: RegistrationLocation
    ): Response<RegistrationLocationResponse>

}
package com.sallyjayz.ranchid.repository

import com.sallyjayz.ranchid.model.register.registrationlocation.RegistrationLocation
import com.sallyjayz.ranchid.service.register.registrationlocation.RegistrationLocationApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class RegistrationLocationRepository @Inject constructor(
    private val registrationLocationApiService: RegistrationLocationApiService
) {

    fun addRegistrationLocation(registrationLocation: RegistrationLocation) = apiRequestFlow {
        registrationLocationApiService.addRegistrationLocation(registrationLocation)
    }

}
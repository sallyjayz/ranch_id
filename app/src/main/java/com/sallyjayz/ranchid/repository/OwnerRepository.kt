package com.sallyjayz.ranchid.repository

import com.sallyjayz.ranchid.model.register.owner.Owner
import com.sallyjayz.ranchid.service.register.owner.OwnerApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class OwnerRepository @Inject constructor(
    private val ownerApiService: OwnerApiService
) {
    /*fun addOwner(token: String,
                 map: MutableMap<String, RequestBody>,
                 requestFile: MultipartBody.Part) = apiRequestFlow {
        ownerApiService.postOwnerResponse(token, map, requestFile)
    }*/

    fun addOwner(owner: Owner) = apiRequestFlow {
        ownerApiService.addOwner(owner)
    }
}
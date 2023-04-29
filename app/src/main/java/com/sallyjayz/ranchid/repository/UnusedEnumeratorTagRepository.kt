package com.sallyjayz.ranchid.repository

import com.sallyjayz.ranchid.service.register.unusedenumeratortag.UnusedEnumeratorTagApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class UnusedEnumeratorTagRepository @Inject constructor(
    private val unusedEnumeratorTagApiService: UnusedEnumeratorTagApiService
) {

    fun getUnusedEnumeratorTag(username: String, tagId: String) = apiRequestFlow {
        unusedEnumeratorTagApiService.getUnusedEnumeratorTag(username, tagId)
    }

}
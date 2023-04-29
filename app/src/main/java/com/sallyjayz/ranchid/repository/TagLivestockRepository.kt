package com.sallyjayz.ranchid.repository

import com.sallyjayz.ranchid.model.register.taglivestock.TagLivestock
import com.sallyjayz.ranchid.service.register.taglivestock.TagLivestockApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class TagLivestockRepository @Inject constructor(
    private val tagLivestockApiService: TagLivestockApiService
) {
    fun addTagLivestock(tagLivestock: TagLivestock) = apiRequestFlow {
        tagLivestockApiService.tagLivestock(tagLivestock)
    }
}
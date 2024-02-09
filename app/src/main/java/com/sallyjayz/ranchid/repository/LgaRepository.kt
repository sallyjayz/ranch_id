package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.LgaDao
import com.sallyjayz.ranchid.model.lga.LGA
import com.sallyjayz.ranchid.service.lga.StateLgaApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class LgaRepository @Inject constructor(
    private val stateLgaApiService: StateLgaApiService,
    private val lgaDao: LgaDao
) {

    fun getLGAs() = apiRequestFlow {
        stateLgaApiService.getAllLGA()
    }

    val readAll: LiveData<List<LGA>> = lgaDao.readAllLGA()

    suspend fun saveLgas(lgas: List<LGA>) =
        lgaDao.insertLGAs(lgas)

    fun getLGAStateId(lgaStateId: Int) = lgaDao.readLGA(lgaStateId)

    fun getLgaName(name: String) = lgaDao.readSelectedLgaName(name)

    fun getLgaAndStateId(id: Int, state_id: Int) = lgaDao.readSelectedLgaAndStateId(id, state_id)

    fun deleteAllLgas() = lgaDao.deleteAllLGA()
}
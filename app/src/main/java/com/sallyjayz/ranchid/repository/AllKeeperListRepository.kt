package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.AllKeepersDao
import com.sallyjayz.ranchid.model.allkeepers.AllKeepers
import com.sallyjayz.ranchid.service.register.taglivestock.TagLivestockApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class AllKeeperListRepository @Inject constructor(
    private val tagLivestockApiService: TagLivestockApiService,
    private val allKeepersDao: AllKeepersDao

) {

    fun getAllKeepers() = apiRequestFlow {
        tagLivestockApiService.getAllKeepersList()
    }

    val readAllKeepers: LiveData<List<AllKeepers>> =
        allKeepersDao.readAllKeepers()

    suspend fun saveAllKeepers(allKeepers: List<AllKeepers>) =
        allKeepersDao.insertAllKeepers(allKeepers)

    fun getKeeperById(keeperId: Int) = allKeepersDao.readKeeperById(keeperId)

    fun getKeeperByName(surname: String, othername: String) =
        allKeepersDao.readSelectedKeeperName(surname, othername)

    fun deleteAllKeepers() = allKeepersDao.deleteAllKeepers()

}
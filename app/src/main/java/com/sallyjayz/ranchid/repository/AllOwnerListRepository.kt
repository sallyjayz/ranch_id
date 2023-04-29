package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.AllOwnersDao
import com.sallyjayz.ranchid.model.allowners.AllOwners
import com.sallyjayz.ranchid.service.register.taglivestock.TagLivestockApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class AllOwnerListRepository @Inject constructor(
    private val tagLivestockApiService: TagLivestockApiService,
    private val allOwnersDao: AllOwnersDao

) {

    fun getAllOwners() = apiRequestFlow {
        tagLivestockApiService.getAllOwnersList()
    }

    val readAllOwners: LiveData<List<AllOwners>> =
        allOwnersDao.readAllOwners()

    suspend fun saveAllOwners(allOwners: List<AllOwners>) =
        allOwnersDao.insertAllOwners(allOwners)

    fun getOwnerById(ownerId: Int) = allOwnersDao.readOwnerById(ownerId)

    fun getOwnerByName(surname: String, othername: String) =
        allOwnersDao.readSelectedOwnerName(surname, othername)

    fun deleteAllOwners() = allOwnersDao.deleteAllOwners()

}
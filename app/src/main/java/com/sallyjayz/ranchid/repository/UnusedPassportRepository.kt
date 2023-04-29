package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.UnusedPassportDao
import com.sallyjayz.ranchid.model.unusedpassport.UnusedPassport
import com.sallyjayz.ranchid.service.register.unusedpassport.UnusedPassportApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class UnusedPassportRepository @Inject constructor(
    private val unusedPassportApiService: UnusedPassportApiService,
    private val unusedPassportDao: UnusedPassportDao
) {
    fun getUnusedPassport(username: String) = apiRequestFlow {
        unusedPassportApiService.getUnusedPassport(username)
    }

    suspend fun saveUnusedPassport(unusedPassport: UnusedPassport) =
        unusedPassportDao.insertUnusedPassport(unusedPassport)

    val readUnusedPassport: LiveData<List<UnusedPassport>> =
        unusedPassportDao.getAllUnusedPassport()

    fun getSelectedUnusedPassport(selectedUnusedPassport: String) =
        unusedPassportDao.getSelectedUnusedPassport(selectedUnusedPassport)

    fun deleteAllUnusedPassport() = unusedPassportDao.deleteAllUnusedPassport()

}
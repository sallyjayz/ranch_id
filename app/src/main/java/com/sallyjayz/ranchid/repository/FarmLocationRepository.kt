package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.FarmLocationDao
import com.sallyjayz.ranchid.model.farmlocation.FarmLocation
import com.sallyjayz.ranchid.service.farmlocation.FarmLocationApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class FarmLocationRepository @Inject constructor(
    private val farmLocationApiService: FarmLocationApiService,
    private val farmLocationDao: FarmLocationDao

) {

    fun getFarmLocation() = apiRequestFlow {
        farmLocationApiService.getAllFarmLocation()
    }

    suspend fun saveLocation(location: List<FarmLocation>) =
        farmLocationDao.insertLocation(location)

    /*suspend fun saveLocation(location: FarmLocation) =
        farmLocationDao.insertLocation(location)*/

    val readAllLocation: LiveData<List<FarmLocation>> =
        farmLocationDao.getAllLocation()

    fun getLocationName(selectedStateName: String) =
        farmLocationDao.getSelectedLocationName(selectedStateName)

    fun deleteAllFarmLocation() = farmLocationDao.deleteAllFarmLocation()

}
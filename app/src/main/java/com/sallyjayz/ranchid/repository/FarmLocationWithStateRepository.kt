package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.FarmLocationWithStateDao
import com.sallyjayz.ranchid.model.farmlocation.FarmLocationWithState
import com.sallyjayz.ranchid.service.farmlocation.FarmLocationWithStateApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

/**
 * Created by Salama Jatau on 16-Jan-25.
 */

class FarmLocationWithStateRepository @Inject constructor(
    private val farmLocationWithStateApiService: FarmLocationWithStateApiService,
    private val farmLocationWithStateDao: FarmLocationWithStateDao

) {

    fun getFarmLocationWithState() = apiRequestFlow {
        farmLocationWithStateApiService.getAllFarmLocationWithState()
    }


    suspend fun saveLocationWithState(location: List<FarmLocationWithState>) =
        farmLocationWithStateDao.insertLocationWithState(location)


    val readAllLocationWithState: LiveData<List<FarmLocationWithState>> =
        farmLocationWithStateDao.getAllLocationWithState()

    fun getStateLgaId(stateId: Int, lgaId: Int) = farmLocationWithStateDao.readLocationWithState(stateId, lgaId)

    fun getLocationName(selectedLocationName: String) =
        farmLocationWithStateDao.getSelectedLocationName(selectedLocationName)

    fun getLocationWithStateId(id: Int) = farmLocationWithStateDao.getSelectedLocationWithStateId(id)

//    fun getLocationStateAndLgaId(id: Int, stateId: Int, lgaId: Int) = farmLocationWithStateDao.readSelectedLgaAndStateId(id, stateId, lgaId)

    fun getLocationStateAndLgaId(stateId: Int, lgaId: Int) = farmLocationWithStateDao.readSelectedLgaAndStateId(stateId, lgaId)

    fun getLocationId(id: Int) = farmLocationWithStateDao.getSelectedLocationId(id)

    fun deleteAllFarmLocationWithState() = farmLocationWithStateDao.deleteAllFarmLocationWithState()

}
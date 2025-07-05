package com.sallyjayz.ranchid.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.model.farmlocation.FarmLocationWithState
import com.sallyjayz.ranchid.repository.FarmLocationWithStateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 16-Jan-25.
 */

@HiltViewModel
class FarmLocationWithStateViewModel @Inject constructor(
    private val farmLocationWithStateRepository: FarmLocationWithStateRepository,
    context: Application
): AndroidViewModel(context) {

    private var _readAllLocationWithState: LiveData<List<FarmLocationWithState>> =
        farmLocationWithStateRepository.readAllLocationWithState

    val readAllLocationWithState: LiveData<List<FarmLocationWithState>>
        get() = _readAllLocationWithState

    fun getStateLgaId(stateId: Int, lgaId: Int) = farmLocationWithStateRepository.getStateLgaId(stateId, lgaId)

    fun getLocationName(name: String) = farmLocationWithStateRepository.getLocationName(name)

    fun getLocationWithStateId(id: Int) = farmLocationWithStateRepository.getLocationWithStateId(id)

//    fun getLocationWithStateLgaId(id: Int, stateId: Int, lgaId: Int) = farmLocationWithStateRepository.getLocationStateAndLgaId(id, stateId, lgaId)

    fun getLocationWithStateLgaId(stateId: Int, lgaId: Int) = farmLocationWithStateRepository.getLocationStateAndLgaId(stateId, lgaId)

    fun getLocationId(id: Int) = farmLocationWithStateRepository.getLocationId(id)

    suspend fun insertLocationsWithStates(location: List<FarmLocationWithState>) = farmLocationWithStateRepository.saveLocationWithState(location)

    fun deleteAllFarmLocationWithState() = farmLocationWithStateRepository.deleteAllFarmLocationWithState()

}
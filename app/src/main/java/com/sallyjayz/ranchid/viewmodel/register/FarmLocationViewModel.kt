package com.sallyjayz.ranchid.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.model.farmlocation.FarmLocation
import com.sallyjayz.ranchid.repository.FarmLocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FarmLocationViewModel @Inject constructor(
    private val farmLocationRepository: FarmLocationRepository,
    context: Application
): AndroidViewModel(context) {

    private var _readAllLocation: LiveData<List<FarmLocation>> =
        farmLocationRepository.readAllLocation

    val readAllLocation: LiveData<List<FarmLocation>>
        get() = _readAllLocation

    fun getLocationName(name: String) = farmLocationRepository.getLocationName(name)

    suspend fun insertLocations(location: List<FarmLocation>) = farmLocationRepository.saveLocation(location)

    /*suspend fun insertLocations(location: FarmLocation) = farmLocationRepository.saveLocation(location)*/

    fun deleteAllFarmLocation() = farmLocationRepository.deleteAllFarmLocation()

}

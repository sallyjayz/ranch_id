package com.sallyjayz.ranchid.viewmodel.packinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Salama Jatau on 19-Jun-23.
 */
class PackingListViewModel : ViewModel() {

    private val _driverName = MutableLiveData<String>()
    val driverName: LiveData<String> = _driverName

    private val _vehicleName = MutableLiveData<String>()
    val vehicleName: LiveData<String> = _vehicleName

    private val _packingLivestockType = MutableLiveData<String>()
    val packingLivestockType: LiveData<String> = _packingLivestockType

    private val _regNumber = MutableLiveData<String>()
    val regNumber: LiveData<String> = _regNumber

    /*private val _livestockQty = MutableLiveData<String>()
    val livestockQty: LiveData<String> = _livestockQty*/

    private val _destination = MutableLiveData<String>()
    val destination: LiveData<String> = _destination

    private val _packingState = MutableLiveData<String>()
    val packingState: LiveData<String> = _packingState

    private val _packingLga = MutableLiveData<String>()
    val packingLga: LiveData<String> = _packingLga

    private val _packingStateId = MutableLiveData<String>()
    val packingStateId: LiveData<String> = _packingStateId

    private val _packingLgaId = MutableLiveData<String>()
    val packingLgaId: LiveData<String> = _packingLgaId

    fun resetPackingList() {
        _driverName.value = ""
        _vehicleName.value = ""
        _regNumber.value = ""
        _packingLivestockType.value = ""
//        _livestockQty.value = ""
        _destination.value = ""
        _packingState.value = ""
        _packingLga.value = ""
    }

    fun resetStepOnePackingList() {
        _driverName.value = ""
        _vehicleName.value = ""
        _regNumber.value = ""
        _packingLivestockType.value = ""
//        _livestockQty.value = ""
        _destination.value = ""
        _packingState.value = ""
        _packingLga.value = ""
    }

    fun setStepOne(driverName: String, vehicleName: String, regNumber: String,
                   livestockType: String,/*livestockQty: String,*/ destination: String,
                   state: String, lga: String, stateId: String, lgaId: String){
        _driverName.value = driverName
        _vehicleName.value = vehicleName
        _regNumber.value = regNumber
        _packingLivestockType.value = livestockType
//        _livestockQty.value = livestockQty
        _destination.value = destination
        _packingState.value = state
        _packingLga.value = lga
        _packingStateId.value = stateId
        _packingLgaId.value = lgaId
    }

    fun isStepOneEntryValid(driverName: String, vehicleName: String, regNumber: String,
                            livestockType: String, /*livestockQty: String,*/ destination: String,
                            state: String, lga: String): Boolean {
        if (driverName.isBlank() || vehicleName.isBlank() || regNumber.isBlank() ||
            livestockType.isBlank() || /*livestockQty.isBlank() ||*/ destination.isBlank()
            || state.isBlank() || lga.isBlank()) {
            return false
        }
        return true

    }

}
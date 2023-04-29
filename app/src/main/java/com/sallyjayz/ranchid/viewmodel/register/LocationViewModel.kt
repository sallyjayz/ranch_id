package com.sallyjayz.ranchid.viewmodel.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel: ViewModel() {

    /*private var _ownerName = ""
    val ownerName: String
        get() = _ownerName

    private var _locationName = ""
    val locationName: String
        get() = _locationName

    private var _address = ""
    val locationAddress: String
        get() = _address

    private var _locationType = ""
    val locationType: String
        get() = _locationType

    private var _locationState = ""
    val locationState: String
        get() = _locationState

    private var _locationLga = ""
    val locationLga: String
        get() = _locationLga

    private var _certificateId = ""
    val certificateId: String
        get() = _certificateId

  fun setLocation(ownerName: String, locationName: String, address: String, locationType: String,
                   locationState: String, locationLga: String, certificateId: String) {
        _ownerName = ownerName
        _locationName = locationName
        _address = address
        _locationType = locationType
        _locationState = locationState
        _locationLga = locationLga
        _certificateId = certificateId
    }

        */



    private val _ownerName = MutableLiveData<String>()
    val ownerName: LiveData<String> = _ownerName

    private val _locationName = MutableLiveData<String>()
    val locationName: LiveData<String> = _locationName

    private val _address = MutableLiveData<String>()
    val locationAddress: LiveData<String> = _address

    private val _locationType = MutableLiveData<String>()
    val locationType: LiveData<String> = _locationType

    private val _locationState = MutableLiveData<String>()
    val locationState: LiveData<String> = _locationState

    private val _locationStateId = MutableLiveData<Int>()
    val locationStateId: LiveData<Int> = _locationStateId

    private val _locationLga = MutableLiveData<String>()
    val locationLga: LiveData<String> = _locationLga

    private val _locationLgaId = MutableLiveData<Int>()
    val locationLgaId: LiveData<Int> = _locationLgaId

    private val _certificate = MutableLiveData<String>()
    val certificate: LiveData<String> = _certificate

    private val _certificateId = MutableLiveData<Int>()
    val certificateId: LiveData<Int> = _certificateId

    init {
        resetLocation()
    }

    fun resetLocation() {
        _ownerName.value = ""
        _locationName.value = ""
        _address.value = ""
        _locationType.value = ""
        _locationState.value = ""
        _locationLga.value = ""
        _certificate.value = ""
        _locationStateId.value = 0
        _locationLgaId.value = 0
        _certificateId.value = 0
    }

    fun setLocation(ownerName: String, locationName: String, address: String, locationType: String,
                   locationState: String, locationLga: String, locationStateId: Int, locationLgaId: Int,
                    certificate: String, certificateId: Int) {
        _ownerName.value = ownerName
        _locationName.value = locationName
        _address.value = address
        _locationType.value = locationType
        _locationState.value = locationState
        _locationLga.value = locationLga
        _locationStateId.value = locationStateId
        _locationLgaId.value = locationLgaId
        _certificateId.value = certificateId
        _certificate.value = certificate
    }

    fun isEntryValid(ownerName: String, locationName: String, address: String, locationType: String,
                     locationState: String, locationLga: String, certificate: String): Boolean {
        if (ownerName.isBlank() || locationName.isBlank() || address.isBlank()
            || locationType.isBlank() || locationState.isBlank() || locationLga.isBlank()
            || certificate.isBlank()) {
            return false
        }
        return true
    }

}
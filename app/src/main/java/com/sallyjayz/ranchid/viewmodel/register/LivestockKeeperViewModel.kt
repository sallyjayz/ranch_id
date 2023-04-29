package com.sallyjayz.ranchid.viewmodel.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LivestockKeeperViewModel : ViewModel() {

    private val _surname = MutableLiveData<String>()
    val surname: LiveData<String> = _surname

    private val _othername = MutableLiveData<String>()
    val othername: LiveData<String> = _othername

    private val _keeperGender= MutableLiveData<String>()
    val keeperGender: LiveData<String> = _keeperGender

    private val _keeperMaritalStatus= MutableLiveData<String>()
    val keeperMaritalStatus: LiveData<String> = _keeperMaritalStatus

    private val _keeperDateOfBirth = MutableLiveData<String>()
    val keeperDateOfBirth: LiveData<String> = _keeperDateOfBirth

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _nin = MutableLiveData<String>()
    val nin: LiveData<String> = _nin

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    /*private val _addressTwo = MutableLiveData<String>()
    val addressTwo: LiveData<String> = _addressTwo*/

    private val _keeperState = MutableLiveData<String>()
    val keeperState: LiveData<String> = _keeperState

    private val _keeperLga = MutableLiveData<String>()
    val keeperLga: LiveData<String> = _keeperLga

    private val _keeperStateId = MutableLiveData<String>()
    val keeperStateId: LiveData<String> = _keeperStateId

    private val _keeperLgaId = MutableLiveData<String>()
    val keeperLgaId: LiveData<String> = _keeperLgaId

    private val _keeperNextOfKin = MutableLiveData<String>()
    val keeperNextOfKin: LiveData<String> = _keeperNextOfKin

    private val _nextOfKinPhoneNumber = MutableLiveData<String>()
    val nextOfKinPhoneNumber: LiveData<String> = _nextOfKinPhoneNumber

    private val _documentType = MutableLiveData<String>()
    val documentType: LiveData<String> = _documentType

    private val _documentNumber = MutableLiveData<String>()
    val documentNumber: LiveData<String> = _documentNumber

    private val _farmLocation = MutableLiveData<String>()
    val farmLocation: LiveData<String> = _farmLocation

    private val _farmLocationId = MutableLiveData<String>()
    val farmLocationId: LiveData<String> = _farmLocationId

    private val _photo = MutableLiveData<String?>()
    val photo: LiveData<String?> = _photo

    private val _photoName = MutableLiveData<String>()
    val photoName: LiveData<String> = _photoName

    private val _photoSize= MutableLiveData<String>()
    val photoSize: LiveData<String> = _photoSize

    init {
        resetLivestockKeeper()
    }

    fun resetLivestockKeeper() {
        _surname.value = ""
        _othername.value = ""
        _keeperGender.value = ""
        _keeperMaritalStatus.value = ""
        _keeperDateOfBirth.value = ""
        _phoneNumber.value = ""
        _nin.value = ""
        _email.value = ""
        _address.value = ""
//        _addressTwo.value = ""
        _keeperState.value = ""
        _keeperLga.value = ""
        _keeperNextOfKin.value = ""
        _nextOfKinPhoneNumber.value = ""
        _documentType.value = ""
        _documentNumber.value = ""
        _farmLocation.value = ""
        _photo.value = ""
        _photoName.value = ""
        _photoSize.value = ""
    }

    fun resetStepOneLivestockKeeper() {
        _surname.value = ""
        _othername.value = ""
        _keeperGender.value = ""
        _keeperMaritalStatus.value = ""
        _keeperDateOfBirth.value = ""
        _phoneNumber.value = ""
        _nin.value = ""
        _email.value = ""
        _address.value = ""
//        _addressTwo.value = ""
        _keeperState.value = ""
        _keeperLga.value = ""
    }

    fun resetStepTwoLivestockKeeper() {
        _keeperNextOfKin.value = ""
        _nextOfKinPhoneNumber.value = ""
        _documentType.value = ""
        _documentNumber.value = ""
        _farmLocation.value = ""
        _photo.value = ""
        _photoName.value = ""
        _photoSize.value = ""
    }

    fun setStepOne(surname: String, othername: String, keeperDateOfBirth: String,
                   phoneNumber: String, nin: String, email: String, address: String,
                   /*addressTwo: String,*/ keeperState: String, keeperLga: String, keeperStateId: String,
                   keeperLgaId: String) {

        _surname.value = surname
        _othername.value = othername
        _keeperDateOfBirth.value = keeperDateOfBirth
        _phoneNumber.value = phoneNumber
        _nin.value = nin
        _email.value = email
        _address.value = address
//        _addressTwo.value = addressTwo
        _keeperState.value = keeperState
        _keeperLga.value = keeperLga
        _keeperStateId.value = keeperStateId
        _keeperLgaId.value = keeperLgaId
    }

    fun isStepOneEntryValid(surname: String, othername: String, keeperDateOfBirth: String,
                            phoneNumber: String, nin: String, /*email: String,*/ address: String,
                            /*addressTwo: String,*/ keeperState: String, keeperLga: String): Boolean {
        if (surname.isBlank() || othername.isBlank() || keeperDateOfBirth.isBlank()
            || phoneNumber.isBlank() || nin.isBlank() || /*email.isBlank() ||*/ address.isBlank()
            /*|| addressTwo.isBlank()*/ || keeperState.isBlank() || keeperLga.isBlank()) {
            return false
        }
        return true
    }

    fun setStepTwo(keeperNextOfKin: String, nextOfKinPhoneNumber: String, documentType: String,
                   documentNumber: String, farmLocation: String, farmLocationId: String, photo: String?,
                   photoName: String, photoSize: String) {

        _keeperNextOfKin.value = keeperNextOfKin
        _nextOfKinPhoneNumber.value = nextOfKinPhoneNumber
        _documentType.value = documentType
        _documentNumber.value = documentNumber
        _farmLocation.value = farmLocation
        _farmLocationId.value = farmLocationId
        _photo.value = photo
        _photoName.value = photoName
        _photoSize.value = photoSize
    }

    fun isStepTwoEntryValid(keeperNextOfKin: String, nextOfKinPhoneNumber: String, documentType: String,
                            documentNumber: String, farmLocation: String, photo: String?,
                            photoName: String, photoSize: String): Boolean {
        if (keeperNextOfKin.isBlank() || nextOfKinPhoneNumber.isBlank() || documentType.isBlank()
            || documentNumber.isBlank() || farmLocation.isBlank() /*|| photo.isBlank()*/
            || photoName.isBlank() || photoSize.isBlank()) {
            return false
        }
        return true
    }

    fun hasNoGenderSet(): Boolean {
        return _keeperGender.value.isNullOrEmpty()
    }

    fun setGender(selectedGender: String) {
        _keeperGender.value = selectedGender
    }

    fun hasNoMaritalStatusSet(): Boolean {
        return _keeperMaritalStatus.value.isNullOrEmpty()
    }

    fun setMaritalStatus(selectedMaritalStatus: String) {
        _keeperMaritalStatus.value = selectedMaritalStatus
    }
}


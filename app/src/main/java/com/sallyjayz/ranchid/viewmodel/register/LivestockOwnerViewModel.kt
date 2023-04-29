package com.sallyjayz.ranchid.viewmodel.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LivestockOwnerViewModel : ViewModel() {

    private val _ownerType = MutableLiveData<String>()
    val ownerType: LiveData<String> = _ownerType

    private val _ownerLivestockKeeper = MutableLiveData<String>()
    val ownerLivestockKeeper: LiveData<String> = _ownerLivestockKeeper

    private val _ownerSurname = MutableLiveData<String>()
    val ownerSurname: LiveData<String> = _ownerSurname

    private val _ownerOthername = MutableLiveData<String>()
    val ownerOthername: LiveData<String> = _ownerOthername

    private val _ownerGender= MutableLiveData<String>()
    val ownerGender: LiveData<String> = _ownerGender

    private val _ownerMaritalStatus= MutableLiveData<String>()
    val ownerMaritalStatus: LiveData<String> = _ownerMaritalStatus

    private val _ownerDateOfBirth = MutableLiveData<String>()
    val ownerDateOfBirth: LiveData<String> = _ownerDateOfBirth

    private val _ownerPhoneNumber = MutableLiveData<String>()
    val ownerPhoneNumber: LiveData<String> = _ownerPhoneNumber

    private val _ownerNin = MutableLiveData<String>()
    val ownerNin: LiveData<String> = _ownerNin

    private val _ownerEmail = MutableLiveData<String>()
    val ownerEmail: LiveData<String> = _ownerEmail

    private val _ownerAddress = MutableLiveData<String>()
    val ownerAddress: LiveData<String> = _ownerAddress

    /*private val _ownerAddressTwo = MutableLiveData<String>()
    val ownerAddressTwo: LiveData<String> = _ownerAddressTwo*/

    private val _ownerState = MutableLiveData<String>()
    val ownerState: LiveData<String> = _ownerState

    private val _ownerLga = MutableLiveData<String>()
    val ownerLga: LiveData<String> = _ownerLga

    private val _ownerStateId = MutableLiveData<String>()
    val ownerStateId: LiveData<String> = _ownerStateId

    private val _ownerLgaId = MutableLiveData<String>()
    val ownerLgaId: LiveData<String> = _ownerLgaId

    private val _ownerNextOfKin = MutableLiveData<String>()
    val ownerNextOfKin: LiveData<String> = _ownerNextOfKin

    private val _ownerKinPhoneNumber = MutableLiveData<String>()
    val ownerKinPhoneNumber: LiveData<String> = _ownerKinPhoneNumber

    private val _ownerDocumentType = MutableLiveData<String>()
    val ownerDocumentType: LiveData<String> = _ownerDocumentType

    private val _ownerDocumentNumber = MutableLiveData<String>()
    val ownerDocumentNumber: LiveData<String> = _ownerDocumentNumber

    private val _ownerFarmLocation = MutableLiveData<String>()
    val ownerFarmLocation: LiveData<String> = _ownerFarmLocation

    private val _ownerFarmLocationId = MutableLiveData<String>()
    val ownerFarmLocationId: LiveData<String> = _ownerFarmLocationId

    private val _ownerCameraPhoto = MutableLiveData<String>()
    val ownerCameraPhoto: LiveData<String> = _ownerCameraPhoto

    private val _ownerPhotoName = MutableLiveData<String>()
    val OwnerPhotoName: LiveData<String> = _ownerPhotoName

    private val _ownerPhotoSize= MutableLiveData<String>()
    val ownerPhotoSize: LiveData<String> = _ownerPhotoSize

    private val _groupName= MutableLiveData<String>()
    val groupName: LiveData<String> = _groupName

    init {
        resetLivestockOwner()
    }

    fun resetLivestockOwner() {
        _ownerType.value = ""
        _groupName.value = ""
        _ownerLivestockKeeper.value = ""
        _ownerSurname.value = ""
        _ownerOthername.value = ""
        _ownerGender.value = ""
        _ownerMaritalStatus.value = ""
        _ownerDateOfBirth.value = ""
        _ownerPhoneNumber.value = ""
        _ownerNin.value = ""
        _ownerEmail.value = ""
        _ownerAddress.value = ""
//        _ownerAddressTwo.value = ""
        _ownerState.value = ""
        _ownerLga.value = ""
        _ownerStateId.value = ""
        _ownerLgaId.value = ""
        _ownerNextOfKin.value = ""
        _ownerKinPhoneNumber.value = ""
        _ownerDocumentType.value = ""
        _ownerDocumentNumber.value = ""
        _ownerFarmLocation.value = ""
        _ownerFarmLocationId.value = ""
        _ownerCameraPhoto.value = ""
        _ownerPhotoName.value = ""
        _ownerPhotoSize.value = ""
    }

    fun resetStepOneLivestockOwner() {
        _ownerType.value = ""
        _groupName.value = ""
        _ownerLivestockKeeper.value = ""
        _ownerSurname.value = ""
        _ownerOthername.value = ""
        _ownerGender.value = ""
        _ownerMaritalStatus.value = ""
        _ownerDateOfBirth.value = ""
        _ownerPhoneNumber.value = ""
        _ownerNin.value = ""
        _ownerEmail.value = ""
        _ownerAddress.value = ""
//        _ownerAddressTwo.value = ""
        _ownerState.value = ""
        _ownerLga.value = ""
        _ownerStateId.value = ""
        _ownerLgaId.value = ""
    }

    fun resetStepTwoLivestockOwner() {
        _ownerNextOfKin.value = ""
        _ownerKinPhoneNumber.value = ""
        _ownerDocumentType.value = ""
        _ownerDocumentNumber.value = ""
        _ownerFarmLocation.value = ""
        _ownerFarmLocationId.value = ""
        _ownerCameraPhoto.value = ""
        _ownerPhotoName.value = ""
        _ownerPhotoSize.value = ""
    }

    fun setStepOne(ownerType: String, surname: String, othername: String,
                   dateOfBirth: String, phoneNumber: String, nin: String,
                   email: String, address: String, /*addressTwo: String,*/
                   ownerState: String, ownerLga: String, ownerStateId: String, ownerLgaId: String) {

        _ownerType.value = ownerType
        _ownerSurname.value = surname
        _ownerOthername.value = othername
        _ownerDateOfBirth.value = dateOfBirth
        _ownerPhoneNumber.value = phoneNumber
        _ownerNin.value = nin
        _ownerEmail.value = email
        _ownerAddress.value = address
//        _ownerAddressTwo.value = addressTwo
        _ownerState.value = ownerState
        _ownerLga.value = ownerLga
        _ownerStateId.value = ownerStateId
        _ownerLgaId.value = ownerLgaId
    }

    fun isStepOneEntryValid(ownerType: String, surname: String, othername: String,
                            dateOfBirth: String, phoneNumber: String, nin: String,
                            /*email: String,*/ address: String, /*addressTwo: String,*/
                            ownerState: String, ownerLga: String): Boolean {
        if (ownerType.isBlank() || surname.isBlank() || othername.isBlank() || dateOfBirth.isBlank()
            || phoneNumber.isBlank() || nin.isBlank() || /*email.isBlank() ||*/ address.isBlank()
            /*|| addressTwo.isBlank()*/ || ownerState.isBlank() || ownerLga.isBlank()) {
            return false
        }
        return true
    }

    fun groupName(groupName: String) {
        _groupName.value = groupName
    }

    fun isGroupNameEntryValid(groupName: String): Boolean {
        if (groupName.isBlank()) {
            return false
        }
        return true
    }

    fun setStepTwo(ownerNextOfKin: String, nextOfKinPhoneNumber: String, documentType: String,
                   documentNumber: String, farmLocation: String, farmLocationId: String,photo: String,
                   photoName: String, photoSize: String) {

        _ownerNextOfKin.value = ownerNextOfKin
        _ownerKinPhoneNumber.value = nextOfKinPhoneNumber
        _ownerDocumentType.value = documentType
        _ownerDocumentNumber.value = documentNumber
        _ownerFarmLocation.value = farmLocation
        _ownerFarmLocationId.value = farmLocationId
        _ownerCameraPhoto.value = photo
        _ownerPhotoName.value = photoName
        _ownerPhotoSize.value = photoSize
    }

    fun isStepTwoEntryValid(ownerNextOfKin: String, nextOfKinPhoneNumber: String, documentType: String,
                            documentNumber: String, farmLocation: String, photo: String,
                            photoName: String, photoSize: String): Boolean {
        if (ownerNextOfKin.isBlank() || nextOfKinPhoneNumber.isBlank() || documentType.isBlank()
            || documentNumber.isBlank() || farmLocation.isBlank() || photo.isBlank()
            || photoName.isBlank() || photoSize.isBlank()) {
            return false
        }
        return true
    }

    fun hasNoGenderSet(): Boolean {
        return _ownerGender.value.isNullOrEmpty()
    }

    fun setGender(selectedGender: String) {
        _ownerGender.value = selectedGender
    }

    fun hasNoMaritalStatusSet(): Boolean {
        return _ownerMaritalStatus.value.isNullOrEmpty()
    }

    fun setMaritalStatus(selectedMaritalStatus: String) {
        _ownerMaritalStatus.value = selectedMaritalStatus
    }

    fun hasNoLivestockKeeperSet(): Boolean {
        return _ownerLivestockKeeper.value.isNullOrEmpty()
    }

    fun setLivestockKeeper(selectedOption: String) {
        _ownerLivestockKeeper.value = selectedOption
    }

}
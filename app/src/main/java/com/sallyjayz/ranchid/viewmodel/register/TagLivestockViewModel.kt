package com.sallyjayz.ranchid.viewmodel.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TagLivestockViewModel : ViewModel() {

    private val _scannedTagId = MutableLiveData<String>()
    val scannedTagId: LiveData<String> = _scannedTagId

    private val _passportId = MutableLiveData<String>()
    val passportId: LiveData<String> = _passportId

    private val _livestockKeeper = MutableLiveData<String>()
    val livestockKeeper : LiveData<String> = _livestockKeeper

    private val _livestockOwner = MutableLiveData<String>()
    val livestockOwner : LiveData<String> = _livestockOwner

    private val _livestockKeeperId = MutableLiveData<String>()
    val livestockKeeperId : LiveData<String> = _livestockKeeperId

    private val _livestockOwnerId = MutableLiveData<String>()
    val livestockOwnerId : LiveData<String> = _livestockOwnerId

    private val _livestockType = MutableLiveData<String>()
    val livestockType : LiveData<String> = _livestockType

    private val _livestockBreed = MutableLiveData<String>()
    val livestockBreed : LiveData<String> = _livestockBreed

    private val _gender = MutableLiveData<String>()
    val gender : LiveData<String> = _gender

    private val _healthStatus = MutableLiveData<String>()
    val healthStatus : LiveData<String> = _healthStatus

    private val _dateOfBirth= MutableLiveData<String>()
    val dateOfBirth : LiveData<String> = _dateOfBirth

    /*private val _birthPeriod = MutableLiveData<String>()
    val birthPeriod : LiveData<String> = _birthPeriod*/

    private val _description = MutableLiveData<String>()
    val description : LiveData<String> = _description

    private val _taggingLocation = MutableLiveData<String>()
    val taggingLocation : LiveData<String> = _taggingLocation

    private val _taggingLocationId = MutableLiveData<String>()
    val taggingLocationId : LiveData<String> = _taggingLocationId

    private val _locationComment = MutableLiveData<String>()
    val locationComment : LiveData<String> = _locationComment

    private val _productionType = MutableLiveData<String>()
    val productionType : LiveData<String> = _productionType

    /*private val _locationCommentTwo = MutableLiveData<String>()
    val locationCommentTwo : LiveData<String> = _locationCommentTwo*/

    private val _verificationPhoto= MutableLiveData<String>()
    val verificationPhoto : LiveData<String> = _verificationPhoto

    private val _verificationPhotoBase64String= MutableLiveData<String>()
    val verificationPhotoBase64String : LiveData<String> = _verificationPhotoBase64String

    private val _verificationPhotoName = MutableLiveData<String>()
    val verificationPhotoName : LiveData<String> = _verificationPhotoName

    private val _verificationPhotoSize = MutableLiveData<String>()
    val verificationPhotoSize : LiveData<String> = _verificationPhotoSize

    private val _muzzlePhoto= MutableLiveData<String>()
    val muzzlePhoto : LiveData<String> = _muzzlePhoto

    private val _muzzlePhotoBase64String= MutableLiveData<String>()
    val muzzlePhotoBase64String : LiveData<String> = _muzzlePhotoBase64String

    private val _muzzlePhotoName = MutableLiveData<String>()
    val muzzlePhotoName : LiveData<String> = _muzzlePhotoName

    private val _muzzlePhotoSize = MutableLiveData<String>()
    val muzzlePhotoSize : LiveData<String> = _muzzlePhotoSize

    var databaseId: Int = 0


    init {
        resetTagLivestock()
    }

    fun setStepOne(scannedId: String, passportId: String, livestockKeeper: String,
                   livestockOwner: String, livestockKeeperId: String,
                   livestockOwnerId: String,livestockType: String, livestockBreed: String,
                   gender: String, healthStatus: String, dateOfBirth: String
        /*, birthPeriod: String*/) {

        _scannedTagId.value = scannedId
        _passportId.value = passportId
        _livestockKeeper.value = livestockKeeper
        _livestockOwner.value = livestockOwner
        _livestockKeeperId.value = livestockKeeperId
        _livestockOwnerId.value = livestockOwnerId
        _livestockType.value = livestockType
        _livestockBreed.value = livestockBreed
        _gender.value = gender
        _healthStatus.value = healthStatus
        _dateOfBirth.value = dateOfBirth
//        _birthPeriod.value = birthPeriod
    }

    fun isStepOneEntryValid(scannedId: String, /*passportId: String,*/ livestockKeeper: String,
                            livestockOwner: String, livestockType: String, livestockBreed: String,
                            gender: String, healthStatus: String, dateOfBirth: String
        /*, birthPeriod: String*/): Boolean {
        if (scannedId.isBlank() || /*passportId.isBlank() ||*/ livestockKeeper.isBlank()
            || livestockOwner.isBlank() || livestockType.isBlank() || livestockBreed.isBlank()
            || gender.isBlank() || healthStatus.isBlank() || dateOfBirth.isBlank()
        /*|| birthPeriod.isBlank()*/) {
            return false
        }
        return true
    }

    fun setStepTwo(description: String, taggingLocation: String, taggingLocationId: String, comment: String,
                   productionType: String, verificationPhoto: String, verificationPhotoBase64: String,
                   verificationName: String, verificationSize: String, muzzlePhoto: String,
                   muzzlePhotoBase64: String, muzzleName: String, muzzleSize: String) {

        _description.value = description
        _taggingLocation.value = taggingLocation
        _taggingLocationId.value = taggingLocationId
        _locationComment.value = comment
        _productionType.value = productionType
//        _locationCommentTwo.value = commentTwo
        _verificationPhoto.value = verificationPhoto
        _verificationPhotoBase64String.value = verificationPhotoBase64
        _verificationPhotoName.value = verificationName
        _verificationPhotoSize.value = verificationSize
        _muzzlePhoto.value = muzzlePhoto
        _muzzlePhotoBase64String.value = muzzlePhotoBase64
        _muzzlePhotoName.value = muzzleName
        _muzzlePhotoSize.value = muzzleSize
    }

    fun isStepTwoEntryValid(description: String, taggingLocation: String, /*comment: String,*/
                            productionType: String, verificationPhoto: String,
                            verificationName: String, verificationSize: String, muzzlePhoto: String,
                            muzzleName: String, muzzleSize: String): Boolean {
        if (description.isBlank() || taggingLocation.isBlank() /*|| comment.isBlank()*/
            || productionType.isBlank() || verificationPhoto.isBlank()
            || verificationName.isBlank() || verificationSize.isBlank() || muzzlePhoto.isBlank()
            || muzzleName.isBlank() || muzzleSize.isBlank()) {
            return false
        }
        return true
    }

    fun resetTagLivestock() {
        _scannedTagId.value = ""
        _passportId.value = ""
        _livestockKeeper.value = ""
        _livestockOwner.value = ""
        _livestockKeeperId.value = ""
        _livestockOwnerId.value = ""
        _livestockType.value = ""
        _livestockBreed.value = ""
        _gender.value = ""
        _healthStatus.value = ""
        _dateOfBirth.value = ""
//        _birthPeriod.value = ""
        _description.value = ""
        _taggingLocation.value = ""
        _taggingLocationId.value = ""
        _locationComment.value = ""
        _productionType.value = ""
//        _locationCommentTwo.value = ""
        _verificationPhoto.value = ""
        _verificationPhotoBase64String.value = ""
        _verificationPhotoName.value = ""
        _verificationPhotoSize.value = ""
        _muzzlePhoto.value = ""
        _muzzlePhotoBase64String.value = ""
        _muzzlePhotoName.value = ""
        _muzzlePhotoSize.value = ""
    }

    fun resetStepTwoTagLivestock() {
        _scannedTagId.value = ""
        _passportId.value = ""
        _livestockKeeper.value = ""
        _livestockOwner.value = ""
        _livestockKeeperId.value = ""
        _livestockOwnerId.value = ""
        _livestockType.value = ""
        _livestockBreed.value = ""
        _gender.value = ""
        _healthStatus.value = ""
        _dateOfBirth.value = ""
//        _birthPeriod.value = ""
    }

    fun resetStepThreeTagLivestock() {
        _description.value = ""
        _taggingLocation.value = ""
        _taggingLocationId.value = ""
        _locationComment.value = ""
        _productionType.value = ""
//        _locationCommentTwo.value = ""
        _verificationPhoto.value = ""
        _verificationPhotoBase64String.value = ""
        _verificationPhotoName.value = ""
        _verificationPhotoSize.value = ""
        _muzzlePhoto.value = ""
        _muzzlePhotoBase64String.value = ""
        _muzzlePhotoName.value = ""
        _muzzlePhotoSize.value = ""
    }

}
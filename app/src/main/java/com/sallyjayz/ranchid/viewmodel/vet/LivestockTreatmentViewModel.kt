package com.sallyjayz.ranchid.viewmodel.vet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Salama Jatau on 27-Jun-24.
 */
class LivestockTreatmentViewModel : ViewModel() {

    private val _scannedId = MutableLiveData<String>()
    val scannedId: LiveData<String> = _scannedId

    private val _timeOfTreatment = MutableLiveData<String>()
    val timeOfTreatment: LiveData<String> = _timeOfTreatment

    private val _dateOfTreatment = MutableLiveData<String>()
    val dateOfTreatment: LiveData<String> = _dateOfTreatment

    private val _treatmentCategory = MutableLiveData<String>()
    val treatmentCategory: LiveData<String> = _treatmentCategory

    private val _treatmentType = MutableLiveData<String>()
    val treatmentType: LiveData<String> = _treatmentType

    private val _vaccine = MutableLiveData<String>()
    val vaccine: LiveData<String> = _vaccine

    private val _dosage = MutableLiveData<String>()
    val dosage: LiveData<String> = _dosage

    private val _frequency = MutableLiveData<String>()
    val frequency: LiveData<String> = _frequency

    private val _administrationRoute = MutableLiveData<String>()
    val administrationRoute: LiveData<String> = _administrationRoute

    private val _injection = MutableLiveData<String>()
    val injection: LiveData<String> = _injection

    private val _diagnosis = MutableLiveData<String>()
    val diagnosis: LiveData<String> = _diagnosis

    private val _photo = MutableLiveData<String?>()
    val photo: LiveData<String?> = _photo

    private val _photoName = MutableLiveData<String>()
    val photoName: LiveData<String> = _photoName

    private val _photoSize = MutableLiveData<String>()
    val photoSize: LiveData<String> = _photoSize

    private val _photoBase64String= MutableLiveData<String>()
    val photoBase64String : LiveData<String> = _photoBase64String

    private val _followUpDate = MutableLiveData<String>()
    val followUpDate: LiveData<String> = _followUpDate

    private val _notes = MutableLiveData<String>()
    val notes: LiveData<String> = _notes

    private val _vetCouncilNumber = MutableLiveData<String>()
    val vetCouncilNumber: LiveData<String> = _vetCouncilNumber

    init {
        resetLivestockTreatment()
    }

    fun resetLivestockTreatment() {
        _scannedId.value = ""
        _timeOfTreatment.value = ""
        _dateOfTreatment.value = ""
        _treatmentCategory.value = ""
        _treatmentType.value = ""
        _vaccine.value = ""
        _dosage.value = ""
        _frequency.value = ""
        _administrationRoute.value = ""
        _injection.value = ""
        _diagnosis.value = ""
        _photo.value = ""
        _photoBase64String.value = ""
        _photoName.value = ""
        _photoSize.value = ""
        _followUpDate.value = ""
        _notes.value = ""
//        _vetCouncilNumber.value = ""
    }

    fun resetLivestockTreatmentOne() {
        _scannedId.value = ""
        _timeOfTreatment.value = ""
        _dateOfTreatment.value = ""
        _treatmentCategory.value = ""
        _treatmentType.value = ""
        _vaccine.value = ""
        _dosage.value = ""
        _frequency.value = ""
        _administrationRoute.value = ""
        _injection.value = ""
        _diagnosis.value = ""
    }

    fun resetLivestockTreatmentTwo() {
        _photo.value = ""
        _photoName.value = ""
        _photoSize.value = ""
        _followUpDate.value = ""
        _notes.value = ""
    }

    fun setStepThree(vetCouncilNumber: String, scannedId: String, timeOfTreatment: String, dateOfTreatment: String,
                     treatmentCategory: String, treatmentType:String, vaccine: String, dosage: String,
                     frequency: String, administrationRoute: String, injection: String, diagnosis: String) {
        _vetCouncilNumber.value = vetCouncilNumber
        _scannedId.value = scannedId
        _timeOfTreatment.value = timeOfTreatment
        _dateOfTreatment.value = dateOfTreatment
        _treatmentCategory.value = treatmentCategory
        _treatmentType.value = treatmentType
        _vaccine.value = vaccine
        _dosage.value = dosage
        _frequency.value = frequency
        _administrationRoute.value = administrationRoute
        _injection.value = injection
        _diagnosis.value = diagnosis
    }

    fun setStepFour(photo: String, photoBase64: String, photoName: String, photoSize: String,
                    followUpDate: String, notes: String) {
        _photo.value = photo
        _photoBase64String.value = photoBase64
        _photoName.value = photoName
        _photoSize.value = photoSize
        _followUpDate.value = followUpDate
        _notes.value = notes
    }

    fun isStepThreeEntryValid(scannedId: String, timeOfTreatment: String, dateOfTreatment: String,
                              treatmentCategory: String, treatmentType: String, vaccine: String, dosage: String,
                              frequency: String, administrationRoute: String, /*injection: String,*/ diagnosis: String): Boolean {
        return !(scannedId.isBlank() || timeOfTreatment.isBlank() || dateOfTreatment.isBlank() || treatmentCategory.isBlank() || treatmentType.isBlank() ||
                vaccine.isBlank() || dosage.isBlank() || frequency.isBlank() || administrationRoute.isBlank() || /*injection.isBlank() ||*/ diagnosis.isBlank())
    }

    fun isStepFourEntryValid(photo: String, photoName: String, photoSize: String, followUpDate: String, notes: String): Boolean {
        return !(photo.isBlank() || photoName.isBlank() || photoSize.isBlank() || followUpDate.isBlank() || notes.isBlank())
    }

}
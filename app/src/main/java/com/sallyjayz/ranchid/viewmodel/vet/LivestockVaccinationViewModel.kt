package com.sallyjayz.ranchid.viewmodel.vet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Salama Jatau on 22-Mar-24.
 */


class LivestockVaccinationViewModel : ViewModel() {

    private val _scannedId = MutableLiveData<String>()
    val scannedId: LiveData<String> = _scannedId

    private val _livestockType = MutableLiveData<String>()
    val livestockType: LiveData<String> = _livestockType

    private val _vaccine = MutableLiveData<String>()
    val vaccine: LiveData<String> = _vaccine

    private val _dosage = MutableLiveData<String>()
    val dosage : LiveData<String> = _dosage

    private val _frequency = MutableLiveData<String>()
    val frequency : LiveData<String> = _frequency

    private val _timeOfVaccination = MutableLiveData<String>()
    val timeOfVaccination : LiveData<String> = _timeOfVaccination

    private val _dateOfVaccination = MutableLiveData<String>()
    val dateOfVaccination  : LiveData<String> = _dateOfVaccination

    private val _dateOfNextAppointment = MutableLiveData<String>()
    val dateOfNextAppointment : LiveData<String> = _dateOfNextAppointment

    private val _notes = MutableLiveData<String>()
    val notes : LiveData<String> = _notes

    private val _vetCouncilNumber = MutableLiveData<String>()
    val vetCouncilNumber: LiveData<String> = _vetCouncilNumber

    private val _photo = MutableLiveData<String?>()
    val photo: LiveData<String?> = _photo

    private val _photoName = MutableLiveData<String>()
    val photoName: LiveData<String> = _photoName

    private val _photoSize= MutableLiveData<String>()
    val photoSize: LiveData<String> = _photoSize

    private val _photoBase64String= MutableLiveData<String>()
    val photoBase64String : LiveData<String> = _photoBase64String

    private val _drugId= MutableLiveData<String>()
    val drugId : LiveData<String> = _drugId

    init {
        resetLivestockVaccination()
    }

    fun resetLivestockVaccination() {
//        _vetCouncilNumber.value = ""
        _scannedId.value = ""
        _livestockType.value = ""
        _vaccine.value = ""
        _dosage.value = ""
        _frequency.value = ""
        _timeOfVaccination.value = ""
        _dateOfVaccination.value = ""
        _dateOfNextAppointment.value = ""
        _notes.value = ""
        _photo.value = ""
        _photoBase64String.value = ""
        _photoName.value = ""
        _photoSize.value = ""
        _drugId.value = ""
    }

    fun setStepThree(vetCouncilNumber: String, scannedId: String, livestockType: String, vaccine: String,
                     drugId:String, dosage: String, frequency: String, timeOfVaccination: String,
                     dateOfVaccination: String, dateOfNextAppointment: String,
                     notes: String, photo: String, photoBase64: String,
                     photoName: String, photoSize: String) {
        _vetCouncilNumber.value = vetCouncilNumber
        _scannedId.value = scannedId
        _livestockType.value = livestockType
        _vaccine.value = vaccine
        _drugId.value = drugId
        _dosage.value = dosage
        _frequency.value = frequency
        _timeOfVaccination.value = timeOfVaccination
        _dateOfVaccination.value = dateOfVaccination
        _dateOfNextAppointment.value = dateOfNextAppointment
        _notes.value = notes
        _photo.value = photo
        _photoBase64String.value = photoBase64
        _photoName.value = photoName
        _photoSize.value = photoSize
    }

    fun isStepThreeEntryValid(scannedId: String, livestockType: String, vaccine: String, dosage: String,
                              frequency: String, timeOfVaccination: String, dateOfVaccination: String,
                              dateOfNextAppointment: String, notes: String, photo: String, photoName: String, photoSize: String): Boolean {
        return !(scannedId.isBlank() || livestockType.isBlank() || vaccine.isBlank() || dosage.isBlank() || frequency.isBlank() ||
                timeOfVaccination.isBlank() || dateOfVaccination.isBlank() ||
                dateOfNextAppointment.isBlank() || notes.isBlank() || photo.isBlank() || photoName.isBlank() || photoSize.isBlank())
    }

    /*fun setVetNumber(vetCouncilNumber: String) {
        _vetCouncilNumber.value = vetCouncilNumber
    }
*/
}
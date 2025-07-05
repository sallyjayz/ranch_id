package com.sallyjayz.ranchid.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.model.unusedpassport.UnusedPassport
import com.sallyjayz.ranchid.repository.UnusedPassportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnusedPassportViewModel @Inject constructor(
    private val unusedPassportRepository: UnusedPassportRepository,
    context: Application
): AndroidViewModel(context) {

    private var _readAllUnusedPassport : LiveData<List<UnusedPassport?>> = unusedPassportRepository.readUnusedPassport
    val readAllUnusedPassport: LiveData<List<UnusedPassport?>>
        get() = _readAllUnusedPassport

    fun getSelectedUnusedPassport(selectedPassport: String) = unusedPassportRepository.getSelectedUnusedPassport(selectedPassport)

    suspend fun insertUnusedPassport(unusedPassport: UnusedPassport) = unusedPassportRepository.saveUnusedPassport(unusedPassport)

    fun deleteAllUnusedPassport() = unusedPassportRepository.deleteAllUnusedPassport()

}
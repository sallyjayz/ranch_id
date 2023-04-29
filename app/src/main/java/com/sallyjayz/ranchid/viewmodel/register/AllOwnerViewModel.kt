package com.sallyjayz.ranchid.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.model.allowners.AllOwners
import com.sallyjayz.ranchid.repository.AllOwnerListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllOwnerViewModel @Inject constructor(
    private val allOwnerListRepository: AllOwnerListRepository,
    context: Application
): AndroidViewModel(context) {

    private var _readAllOwnerList : LiveData<List<AllOwners>> = allOwnerListRepository.readAllOwners
    val readAllOwnerList: LiveData<List<AllOwners>>
        get() = _readAllOwnerList

    fun getSelectedOwnerName(surname: String, othername: String) =
        allOwnerListRepository.getOwnerByName(surname, othername)

    fun getOwnerById(id: Int) = allOwnerListRepository.getOwnerById(id)

    suspend fun insertAllOwners(allOwner: List<AllOwners>) =
        allOwnerListRepository.saveAllOwners(allOwner)

    fun deleteAllOwners() = allOwnerListRepository.deleteAllOwners()

}
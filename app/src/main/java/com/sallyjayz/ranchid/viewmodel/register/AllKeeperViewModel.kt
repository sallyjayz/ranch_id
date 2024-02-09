package com.sallyjayz.ranchid.viewmodel.register

import android.app.Application
import androidx.lifecycle.*
import com.sallyjayz.ranchid.model.allkeepers.AllKeepers
import com.sallyjayz.ranchid.repository.AllKeeperListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllKeeperViewModel @Inject constructor(
    private val allKeeperListRepository: AllKeeperListRepository,
    context: Application
): AndroidViewModel(context) {

    private var _readAllKeeperList : LiveData<List<AllKeepers>> = allKeeperListRepository.readAllKeepers
    val readAllKeeperList: LiveData<List<AllKeepers>>
        get() = _readAllKeeperList

    fun getSelectedKeeperName(surname: String, othername: String) =
        allKeeperListRepository.getKeeperByName(surname, othername)

    fun getKeeperById(id: Int) = allKeeperListRepository.getKeeperById(id)

    suspend fun insertAllKeepers(allKeeper: List<AllKeepers>) =
        allKeeperListRepository.saveAllKeepers(allKeeper)

    fun deleteAllKeepers() = allKeeperListRepository.deleteAllKeepers()

}
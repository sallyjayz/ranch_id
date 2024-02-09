package com.sallyjayz.ranchid.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.model.lga.LGA
import com.sallyjayz.ranchid.repository.LgaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LgaViewModel @Inject constructor(
    private val lgaRepository: LgaRepository,
    context: Application
): AndroidViewModel(context) {

    private var _readAllLga : LiveData<List<LGA>> = lgaRepository.readAll
    val readAllLga: LiveData<List<LGA>>
        get() = _readAllLga

    fun getLgaName(name: String) = lgaRepository.getLgaName(name)

    fun getLgaAndStateId(id: Int, state_id: Int) = lgaRepository.getLgaAndStateId(id, state_id)

    fun getLgaStateId(id: Int) = lgaRepository.getLGAStateId(id)

    suspend fun insertLgas(lgas: List<LGA>) = lgaRepository.saveLgas(lgas)

    fun deleteAllLgas() = lgaRepository.deleteAllLgas()

}
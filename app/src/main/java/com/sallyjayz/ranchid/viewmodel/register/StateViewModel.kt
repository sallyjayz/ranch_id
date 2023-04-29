package com.sallyjayz.ranchid.viewmodel.register

import android.app.Application
import androidx.lifecycle.*
import com.sallyjayz.ranchid.model.state.States
import com.sallyjayz.ranchid.repository.StateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StateViewModel @Inject constructor(
    private val stateRepository: StateRepository,
    context: Application
): AndroidViewModel(context) {

    private var _readAllState : LiveData<List<States>> = stateRepository.readAll
    val readAllState: LiveData<List<States>>
        get() = _readAllState

    fun getStateName(name: String) = stateRepository.getStateName(name)

    suspend fun insertStates(states: List<States>) = stateRepository.saveStates(states)

    fun deleteAllStates() = stateRepository.deleteAllStates()
}


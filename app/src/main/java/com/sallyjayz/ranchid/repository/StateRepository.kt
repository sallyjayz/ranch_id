package com.sallyjayz.ranchid.repository

import androidx.lifecycle.LiveData
import com.sallyjayz.ranchid.database.StateDao
import com.sallyjayz.ranchid.model.state.States
import com.sallyjayz.ranchid.service.lga.StateLgaApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class StateRepository @Inject constructor(
    private val stateLgaApiService: StateLgaApiService,
    private val stateDao: StateDao
) {

    fun getStates() = apiRequestFlow {
        stateLgaApiService.getAllState()
    }

    val readAll: LiveData<List<States>> = stateDao.readAllState()

    suspend fun saveStates(states: List<States>) =
        stateDao.insertStates(states)

    fun getStateName(name: String) = stateDao.readSelectedStateName(name)

    fun getStateId(id: Int) = stateDao.readSelectedStateId(id)

    fun deleteAllStates() = stateDao.deleteAllStates()

}
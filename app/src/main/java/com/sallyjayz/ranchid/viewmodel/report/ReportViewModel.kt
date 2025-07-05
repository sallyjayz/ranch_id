package com.sallyjayz.ranchid.viewmodel.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sallyjayz.ranchid.model.report.keeper.Keeper
import com.sallyjayz.ranchid.model.report.location.Location
import com.sallyjayz.ranchid.model.report.owner.Owner
import com.sallyjayz.ranchid.repository.ReportListRepository
import com.sallyjayz.ranchid.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Salama Jatau on 05-Aug-23.
 */

/*
@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportListRepository: ReportListRepository
): ViewModel() {

    val listKeeperReport: LiveData<PagingData<Keeper>> =
        reportListRepository.getAllKeepersStream().cachedIn(viewModelScope)

    val listOwnerReport: LiveData<PagingData<Owner>> =
        reportListRepository.getAllOwnersStream().cachedIn(viewModelScope)

    fun locationReport(username: String): LiveData<PagingData<Location>> =
        reportListRepository.getAllLocationStream(username).cachedIn(viewModelScope)
}*/

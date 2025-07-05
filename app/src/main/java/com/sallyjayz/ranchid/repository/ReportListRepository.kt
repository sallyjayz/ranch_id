package com.sallyjayz.ranchid.repository

import com.sallyjayz.ranchid.service.report.ReportListApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

/**
 * Created by Salama Jatau on 05-Aug-23.
 */


/*
class ReportListRepository @Inject constructor(
    private val reportListApiService: ReportListApiService
) {

    companion object {
        const val NETWORK_PAGE_SIZE = 100
    }

    fun getAllKeepersStream(): LiveData<PagingData<Keeper>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            KeeperListRecordPagingSource(reportListApiService)
        }
    ).liveData.map {
        val keeperMap = mutableSetOf<Int>()
        it.filter { keeper ->
            if (keeperMap.contains(keeper.id)) {
                false
            }else {
                keeperMap.add(keeper.id)
            }
        }
    }

    fun getAllOwnersStream(): LiveData<PagingData<Owner>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            OwnerListRecordPagingSource(reportListApiService)
        }
    ).liveData.map {
        val ownerMap = mutableSetOf<Int>()
        it.filter { owner ->
            if (ownerMap.contains(owner.id)) {
                false
            }else {
                ownerMap.add(owner.id)
            }
        }
    }

    fun getAllLocationStream(username: String) = Pager(
        config = PagingConfig(
        pageSize = NETWORK_PAGE_SIZE,
        enablePlaceholders = false
        ),
        pagingSourceFactory = {
            LocationListRecordPagingSource(reportListApiService, username)
        }
    ).liveData.map {
        val locationMap = mutableSetOf<Int>()
        it.filter { location ->
            if (locationMap.contains(location.id)) {
                false
            }else {
                locationMap.add(location.id)
            }
        }
    }

    */
/*fun getAllKeepersStream(query: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            ListKeeperRecordPagingSource(tagLivestockApiService, query)
        }
    ).liveData*//*


    */
/*fun getAllKeepersStream() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            ListKeeperRecordPagingSource(tagLivestockApiService)
        }
    ).liveData*//*


}*/



class ReportListRepository @Inject constructor(
    private val reportListApiService: ReportListApiService
) {

    fun getAllKeepers() = apiRequestFlow {
        reportListApiService.getAllKeepersList()
    }

    fun getAllOwners() = apiRequestFlow {
        reportListApiService.getAllOwnersList()
    }

}

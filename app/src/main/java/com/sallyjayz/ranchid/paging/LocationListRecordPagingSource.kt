package com.sallyjayz.ranchid.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sallyjayz.ranchid.model.report.location.Location
import com.sallyjayz.ranchid.service.report.ReportListApiService
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Salama Jatau on 09-Aug-23.
 */

const val LOCATION_STARTING_INDEX = 1

class LocationListRecordPagingSource(
    private val reportListApiService: ReportListApiService,
    val username: String):
    PagingSource<Int, Location>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {

        val position = params.key ?: LOCATION_STARTING_INDEX

        return try {
            val data = reportListApiService.getLocationsList(username, position, params.loadSize)
            LoadResult.Page(
                data = data.locations.sortedBy{ it.location_name },
                prevKey = if (position == LOCATION_STARTING_INDEX) null else position - 1,
                nextKey = if (data.locations.isEmpty()) null else position + 1
            )
        } catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Location>): Int? {
        TODO("Not yet implemented")
    }
}
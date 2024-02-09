package com.sallyjayz.ranchid.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sallyjayz.ranchid.model.report.owner.Owner
import com.sallyjayz.ranchid.service.report.ReportListApiService
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Salama Jatau on 05-Aug-23.
 */

const val OWNER_STARTING_INDEX = 1

class OwnerListRecordPagingSource(private val reportListApiService: ReportListApiService):
    PagingSource<Int, Owner>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Owner> {

        val position = params.key ?: OWNER_STARTING_INDEX

        return try {
            val data = reportListApiService.getOwnersList(position, params.loadSize)
            LoadResult.Page(
                data = data.owners.sortedBy{ it.surname },
                prevKey = if (position == OWNER_STARTING_INDEX) null else position - 1,
                nextKey = if (data.owners.isEmpty()) null else position + 1
            )
        } catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Owner>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
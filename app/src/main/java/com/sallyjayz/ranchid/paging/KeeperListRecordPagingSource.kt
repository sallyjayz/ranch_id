/*
package com.sallyjayz.ranchid.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sallyjayz.ranchid.model.report.keeper.Keeper
import com.sallyjayz.ranchid.service.report.ReportListApiService
import retrofit2.HttpException
import java.io.IOException

*/
/**
 * Created by Salama Jatau on 05-Aug-23.
 *//*


const val KEEPER_STARTING_INDEX = 1

class KeeperListRecordPagingSource(private val reportListApiService: ReportListApiService):
    PagingSource<Int, Keeper>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Keeper> {

        val position = params.key ?: KEEPER_STARTING_INDEX

        return try {
            val data = reportListApiService.getKeepersList(position, params.loadSize)
            LoadResult.Page(
                data = data.keepers.sortedBy{ it.surname },
                prevKey = if (position == KEEPER_STARTING_INDEX) null else position - 1,
                nextKey = if (data.keepers.isEmpty()) null else position + 1

            )
        } catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Keeper>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}*/

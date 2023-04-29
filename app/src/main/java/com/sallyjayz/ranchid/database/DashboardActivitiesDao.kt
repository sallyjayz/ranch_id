package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.dashboard.DashboardExit
import com.sallyjayz.ranchid.model.dashboard.DashboardKeepers
import com.sallyjayz.ranchid.model.dashboard.DashboardOwners
import com.sallyjayz.ranchid.model.dashboard.DashboardTagged

@Dao
interface DashboardActivitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDashboardKeepers(keepers: DashboardKeepers)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDashboardOwners(owners: DashboardOwners)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDashboardExit(exit: DashboardExit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDashboardTagged(tagged: DashboardTagged)

    @Query("SELECT * FROM dashboard_keeper")
    fun readAllDashboardKeeper(): LiveData<DashboardKeepers>

    @Query("DELETE FROM dashboard_keeper")
    fun deleteDashboardKeeper()

    @Query("SELECT * FROM dashboard_exit")
    fun readAllDashboardExit(): LiveData<DashboardExit>

    @Query("DELETE FROM dashboard_exit")
    fun deleteDashboardExit()

    @Query("SELECT * FROM dashboard_owner")
    fun readAllDashboardOwner(): LiveData<DashboardOwners>

    @Query("DELETE FROM dashboard_owner")
    fun deleteDashboardOwner()

    @Query("SELECT * FROM dashboard_tagged")
    fun readAllDashboardTagged(): LiveData<DashboardTagged>

    @Query("DELETE FROM dashboard_tagged")
    fun deleteDashboardTagged()
}
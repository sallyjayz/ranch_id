package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sallyjayz.ranchid.model.vet.vetdashboard.UpcomingAppointments;
import com.sallyjayz.ranchid.model.vet.vetdashboard.Vaccinations;

/**
 * Created by Salama Jatau on 18-Mar-24.
 */

@Dao
interface VetDashboardActivitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVetDashboardAppointment(appointment: UpcomingAppointments)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVetDashboardVaccination(vaccination: Vaccinations)

    @Query("SELECT * FROM upcoming_appointments")
    fun readAllVetDashboardAppointment(): LiveData<UpcomingAppointments>

    @Query("SELECT * FROM vaccinate_livestock")
    fun readAllVetDashboardVaccination(): LiveData<Vaccinations>

    @Query("DELETE FROM upcoming_appointments")
    fun deleteVetDashboardAppointment()

    @Query("DELETE FROM vaccinate_livestock")
    fun deleteVetDashboardVaccination()

}

package com.sallyjayz.ranchid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sallyjayz.ranchid.model.farmlocation.FarmLocationWithState

/**
 * Created by Salama Jatau on 15-Jan-25.
 */

@Dao
interface FarmLocationWithStateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationWithState(farmLocationState: List<FarmLocationWithState>)

    @Query("SELECT * FROM farm_location_state_lga")
    fun getAllLocationWithState(): LiveData<List<FarmLocationWithState>>

    @Query("SELECT * FROM farm_location_state_lga WHERE state = :stateId AND lga = :lgaId")
    fun readLocationWithState(stateId: Int, lgaId: Int): LiveData<List<FarmLocationWithState>>

    @Query("SELECT * FROM farm_location_state_lga WHERE location_name = :selectedLocationName")
    fun getSelectedLocationName(selectedLocationName: String): LiveData<FarmLocationWithState>

    @Query("SELECT * FROM farm_location_state_lga WHERE id = :id")
    fun getSelectedLocationWithStateId(id: Int): LiveData<FarmLocationWithState>

    @Query("SELECT * FROM farm_location_state_lga WHERE state = :stateId AND lga = :lgaId")
    fun readSelectedLgaAndStateId(stateId: Int, lgaId: Int): LiveData<FarmLocationWithState>

    @Query("SELECT * FROM farm_location_state_lga WHERE id = :id")
    fun getSelectedLocationId(id: Int): LiveData<FarmLocationWithState>

    /*@Query("SELECT * FROM farm_location_state_lga WHERE id = :id AND state = :state_id AND lga = :lga_id")
    fun readSelectedLgaAndStateId(id: Int, state_id: Int, lga_id: Int): LiveData<FarmLocationWithState>*/

    @Query("DELETE FROM farm_location_state_lga")
    fun deleteAllFarmLocationWithState()

}
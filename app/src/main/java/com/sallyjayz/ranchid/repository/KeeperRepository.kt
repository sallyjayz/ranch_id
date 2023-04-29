package com.sallyjayz.ranchid.repository

import com.sallyjayz.ranchid.model.register.keeper.Keeper
import com.sallyjayz.ranchid.service.register.keeper.KeeperApiService
import com.sallyjayz.ranchid.utils.apiRequestFlow
import javax.inject.Inject

class KeeperRepository @Inject constructor(
    private val keeperApiService: KeeperApiService
) {

    fun addKeeper(keeper: Keeper) = apiRequestFlow {
        keeperApiService.addKeeper(keeper)
    }

}
package com.sallyjayz.ranchid.model.allowners

import com.google.gson.annotations.SerializedName

/**
 * Created by Salama Jatau on 08-Feb-25.
 */
data class Data(
    @SerializedName("data")
    val allOwnersList: List<AllOwners>
)

package com.sallyjayz.ranchid.model.lga

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class LGAResponse(
    @SerializedName("data")
    val data : List<Data>
)

@Entity
data class Data(
    /*@SerializedName("state_id") val state_id : Int,
    @SerializedName("name") val name : String,*/
    @SerializedName("locals")
    val locals : List<LGA>
)

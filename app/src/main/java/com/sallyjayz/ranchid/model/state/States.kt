package com.sallyjayz.ranchid.model.state

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "states")
class States (
    @PrimaryKey
    @SerializedName("state_id")
    val id: Int,
    val name: String
)
package com.sallyjayz.ranchid.model.lga

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "lga")
class LGA (
    @PrimaryKey
    @SerializedName("local_id")
    val id: Int,
    val state_id: Int,
    @SerializedName("local_name")
    val name: String
)
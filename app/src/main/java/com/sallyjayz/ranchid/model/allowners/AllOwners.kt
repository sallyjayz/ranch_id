package com.sallyjayz.ranchid.model.allowners

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "allowners")
data class AllOwners(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("other_names")
    val other_names: String
)

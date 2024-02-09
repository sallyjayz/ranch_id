package com.sallyjayz.ranchid.model.register.packinglist.packinglist

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Salama Jatau on 01-Jul-23.
 */

@Entity(tableName = "packing_list")
data class AllPackingList(
    /*@PrimaryKey(autoGenerate = true)
    val id: Int,*/
    val status: String,
    @PrimaryKey(autoGenerate = false)
    val tags: String
)

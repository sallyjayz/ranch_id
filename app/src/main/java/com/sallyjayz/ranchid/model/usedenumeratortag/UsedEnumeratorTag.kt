package com.sallyjayz.ranchid.model.usedenumeratortag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "used_enumerator_tag")
data class UsedEnumeratorTag(
    /*val batch_id: Int,
    val country_code: String,
    val created_at: Any,
    val date_assigned: String,
    val date_assigned_c: String,
    val date_assigned_e: String,
    val date_of_entry: String,
    val date_tagged: String,
    val del_flg: String,
    val deleted_at: Any,
    val enumerator: String,
    val first_four: Int,*/
    @PrimaryKey
    val id: Int,
    /*val print_status: String,
    val qr_code: String,
    val rand_code: String,
    val second_four: Int,
    val serial_number: String,
    val state_code: String,
    val tag_colour: Any,*/
    val tag_id: String,
    val tag_type: String,
    /*val tagging_contractor: String,
    val timestamp: Any,
    val updated_at: String,
    val used_flg: String*/
)
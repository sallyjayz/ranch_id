package com.sallyjayz.ranchid.model.register.taglivestock

data class Data(
    val id: Int,
    val tag_id: String,
    val passport_id: String,
    val keeper_id: String,
    val owner_id: String,
    val livestock_type: String,
    val livestock_breed: String,
    val gender: String,
    val health_status: String,
    val gestation_date: String,
    val description: String,
    val verification_photo: String,
    val muzzle_photo: String,
    val tagging_loc_id: Int,
    val other_comments: String,
    val weight: String,
    val production_type: String,
    val captured_by: String
)

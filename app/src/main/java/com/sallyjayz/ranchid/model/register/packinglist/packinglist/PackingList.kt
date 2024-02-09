package com.sallyjayz.ranchid.model.register.packinglist.packinglist

data class PackingList(
    val destination_address: String,
    val driver_name: String,
    val lga: String,
    val livestock_type: String,
    val registration_number: String,
    val state: String,
    val id: Int,
    val status: String,
    val tags: List<String>,
    val vehicle_name: String
)

/*@Entity
data class ParkingList(
    *//*val created_at: String,
    val created_by: String,
    val del_flg: String,
    val deleted_by: String,
    val destination_address: String,
    val driver_name: String,
    val lga: String,
    val livestock_type: String,
    val registration_number: String,
    val state: String,*//*
    @PrimaryKey
    @SerializedName("id")
    val parkingListId: Int,
    val status: String,
//    val tags: List<String>,
*//*    val updated_at: String,
    val vehicle_name: String*//*
)

@Entity
data class Tag(
    @PrimaryKey
    val id: Int,
    val parkingListTagId: Int,
    val tag: String
)

data class ParkingListWithTags(
    @Embedded
    val parkingList: ParkingList,
    @Relation(
        parentColumn = "parkingListId",
        entityColumn = "parkingListTagId"
    )
    val tags: List<Tag>
)*/


package com.sallyjayz.ranchid.model.register.packinglist.scannedlivestock

import com.google.gson.annotations.SerializedName

data class ScanLivestockResponse(
//    val `data`: Data,
    @SerializedName("data")
    val scanLivestock: ScanLivestock,
    val status: String
)
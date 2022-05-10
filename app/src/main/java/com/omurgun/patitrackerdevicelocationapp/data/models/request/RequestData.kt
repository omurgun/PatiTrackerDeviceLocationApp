package com.omurgun.patitrackerdevicelocationapp.data.models.request

import com.google.gson.annotations.SerializedName


data class RequestData(
    @SerializedName("device_id")
    val deviceId : String,
    val data : List<RequestDeviceData>
)
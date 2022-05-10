package com.omurgun.patitrackerdevicelocationapp.data.models.request


data class RequestDeviceData(
    val lng : Double,
    val lat : Double,
    val battery : Double,
    val timestamp : String
)
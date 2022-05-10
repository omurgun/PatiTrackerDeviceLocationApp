package com.omurgun.patitrackerdevicelocationapp.data.models.request


data class RequestDeviceData(
    val lat : Double,
    val lng : Double,
    val battery : Double,
    val timestamp : String
)
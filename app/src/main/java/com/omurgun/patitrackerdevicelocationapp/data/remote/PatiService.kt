package com.omurgun.patitrackerdevicelocationapp.data.remote

import com.omurgun.patitrackerdevicelocationapp.application.constants.ApplicationNetworkConstants.CONSTANT_SEND_DATA
import com.omurgun.patitrackerdevicelocationapp.data.models.request.RequestData
import com.omurgun.patitrackerdevicelocationapp.data.models.response.ResponseData
import retrofit2.http.Body
import retrofit2.http.POST

interface PatiService {
    @POST(CONSTANT_SEND_DATA)
    suspend fun sendData(@Body requestData: RequestData) : ResponseData
}
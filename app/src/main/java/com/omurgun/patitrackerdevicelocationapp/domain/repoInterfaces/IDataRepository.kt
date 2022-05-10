package com.omurgun.patitrackerdevicelocationapp.domain.repoInterfaces

import com.omurgun.patitrackerdevicelocationapp.data.models.request.RequestData
import com.omurgun.patitrackerdevicelocationapp.data.models.response.ResponseData

interface IDataRepository {
    suspend fun sendData(requestData: RequestData) : ResponseData
}
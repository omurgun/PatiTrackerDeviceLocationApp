package com.omurgun.patitrackerdevicelocationapp.data.repo

import com.omurgun.patitrackerdevicelocationapp.data.models.request.RequestData
import com.omurgun.patitrackerdevicelocationapp.data.models.response.ResponseData
import com.omurgun.patitrackerdevicelocationapp.data.remote.PatiService
import com.omurgun.patitrackerdevicelocationapp.domain.repoInterfaces.IDataRepository
import javax.inject.Inject

class DataRepository @Inject constructor(private val patiService: PatiService) : IDataRepository {
    override suspend fun sendData(requestData: RequestData): ResponseData {
        return patiService.sendData(requestData)
    }

}
package com.omurgun.patitrackerdevicelocationapp.domain.useCases


import com.omurgun.patitrackerdevicelocationapp.data.models.request.RequestData
import com.omurgun.patitrackerdevicelocationapp.data.models.response.ResponseData
import com.omurgun.patitrackerdevicelocationapp.domain.repoInterfaces.IDataRepository
import com.omurgun.patitrackerdevicelocationapp.util.ResultData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError


class DataUseCase @Inject constructor(private val dataRepository: IDataRepository) {

    fun sendDataUseCase(requestData: RequestData) : Flow<ResultData<ResponseData>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = dataRepository.sendData(requestData)
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }
}
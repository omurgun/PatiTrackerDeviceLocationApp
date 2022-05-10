package com.omurgun.patitrackerdevicelocationapp.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.omurgun.patitrackerdevicelocationapp.data.models.request.RequestData
import com.omurgun.patitrackerdevicelocationapp.data.models.response.ResponseData
import com.omurgun.patitrackerdevicelocationapp.domain.useCases.DataUseCase
import com.omurgun.patitrackerdevicelocationapp.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataUseCase: DataUseCase) : ViewModel() {

    fun sendDataFromAPI(requestData: RequestData) : LiveData<ResultData<ResponseData>> {
        return dataUseCase.sendDataUseCase(requestData).asLiveData(Dispatchers.IO)
    }

}
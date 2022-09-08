package com.omurgun.patitrackerdevicelocationapp.ui.viewModels

import android.content.ServiceConnection
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.omurgun.patitrackerdevicelocationapp.data.local.dataStore.LocationPreferences
import com.omurgun.patitrackerdevicelocationapp.data.models.request.RequestData
import com.omurgun.patitrackerdevicelocationapp.data.models.response.ResponseData
import com.omurgun.patitrackerdevicelocationapp.data.repo.LocationRepository
import com.omurgun.patitrackerdevicelocationapp.domain.useCases.DataUseCase
import com.omurgun.patitrackerdevicelocationapp.service.ForegroundLocationServiceConnection
import com.omurgun.patitrackerdevicelocationapp.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataUseCase: DataUseCase,
    private val locationPreferences: LocationPreferences,
    private val serviceConnection: ForegroundLocationServiceConnection,
    private val locationRepository: LocationRepository
    ) : ViewModel(), ServiceConnection by serviceConnection {

    val isReceivingLocationUpdates = locationRepository.isReceivingLocationUpdates
    val lastLocation = locationRepository.lastLocation

    fun sendDataFromAPI(requestData: RequestData) : LiveData<ResultData<ResponseData>> {
        return dataUseCase.sendDataUseCase(requestData).asLiveData(Dispatchers.IO)
    }


    fun toggleLocationUpdates() {
        if (isReceivingLocationUpdates.value) {
            stopLocationUpdates()
        } else {
            startLocationUpdates()
        }
    }

    fun saveDeviceId(deviceId: String) {
        viewModelScope.launch {
            locationPreferences.setDeviceId(deviceId)
        }

    }

    suspend fun getDeviceId() : String {
        return locationPreferences.getDeviceId.first()

    }

    private fun startLocationUpdates() {
        serviceConnection.service?.startLocationUpdates()
        // Store that the user turned on location updates.
        // It's possible that the service was not connected for the above call. In that case, when
        // the service eventually starts, it will check the persisted value and react appropriately.
        viewModelScope.launch {
            locationPreferences.setLocationTurnedOn(true)
        }
    }

    private fun stopLocationUpdates() {
        serviceConnection.service?.stopLocationUpdates()
        // Store that the user turned off location updates.
        // It's possible that the service was not connected for the above call. In that case, when
        // the service eventually starts, it will check the persisted value and react appropriately.
        viewModelScope.launch {
            locationPreferences.setLocationTurnedOn(false)
        }
    }


}
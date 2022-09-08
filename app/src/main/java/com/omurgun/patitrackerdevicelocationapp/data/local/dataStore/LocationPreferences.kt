package com.omurgun.patitrackerdevicelocationapp.data.local.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    val isLocationTurnedOn = dataStore.data.map {
        it[locationOnKey] ?: false
    }

    val getDeviceId = dataStore.data.map {
        it[deviceIdd] ?: "11111112"
    }

    suspend fun setLocationTurnedOn(isStarted: Boolean) = withContext(Dispatchers.IO) {
        dataStore.edit {
            it[locationOnKey] = isStarted
        }
    }

    suspend fun setDeviceId(deviceId: String) = withContext(Dispatchers.IO) {
        dataStore.edit {
            it[deviceIdd] = deviceId
        }
    }

    private companion object {
        val locationOnKey = booleanPreferencesKey("is_location_on")
        val deviceIdd = stringPreferencesKey("device_id")
    }
}

package com.omurgun.patitrackerdevicelocationapp.ui.activities

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import com.omurgun.patitrackerdevicelocationapp.util.GPSUtils
import com.omurgun.patitrackerdevicelocationapp.util.hasPermission
import com.omurgun.patitrackerdevicelocationapp.util.shouldShowRationaleFor

private val locationPermissions =
    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)


class LocationPermissionState(
    private val activity: ComponentActivity,
    val onResult: (LocationPermissionState) -> Unit
) {

    /** Whether permission was granted to access approximate location. */
    var accessCoarseLocationGranted = MutableLiveData<Boolean>().apply { setValue(false) }
        private set

    /** Whether to show a rationale for permission to access approximate location. */
    var accessCoarseLocationNeedsRationale = MutableLiveData<Boolean>().apply { setValue(false) }
        private set

    /** Whether permission was granted to access precise location. */
    var accessFineLocationGranted = MutableLiveData<Boolean>().apply { setValue(false) }
        private set

    /** Whether to show a rationale for permission to access precise location. */
    var accessFineLocationNeedsRationale = MutableLiveData<Boolean>().apply { setValue(false) }
        private set

    /**
     * Whether to show a degraded experience (set after the permission is denied).
     */
    var showDegradedExperience = MutableLiveData<Boolean>().apply { setValue(false) }
        private set

    private val permissionLauncher: ActivityResultLauncher<Array<String>> =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            GPSUtils(activity).turnOnGPS()
            updateState()
            showDegradedExperience.value = !hasPermission()
            onResult(this)
        }

    init {
        updateState()
    }

    fun updateState() {
        accessCoarseLocationGranted.value = activity.hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        accessCoarseLocationNeedsRationale.value =
            activity.shouldShowRationaleFor(Manifest.permission.ACCESS_COARSE_LOCATION)
        accessFineLocationGranted.value = activity.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        accessFineLocationNeedsRationale.value =
            activity.shouldShowRationaleFor(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    /**
     * Launch the permission request. Note that this may or may not show the permission UI if the
     * permission has already been granted or if the user has denied permission multiple times.
     */
    fun requestPermissions() {
        permissionLauncher.launch(locationPermissions)
    }

    fun hasPermission(): Boolean = accessCoarseLocationGranted.value ?: false || accessFineLocationGranted.value ?: false

    fun shouldShowRationale(): Boolean = !hasPermission() && (
            accessCoarseLocationNeedsRationale.value ?: false || accessFineLocationNeedsRationale.value ?: false)
}

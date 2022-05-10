package com.omurgun.patitrackerdevicelocationapp.util

import android.app.Activity
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

class GPSUtils(context: Context) {
    private val mContext : Context = context
    private var mSettingClient : SettingsClient? = null
    private var mLocationSettingsRequest : LocationSettingsRequest? = null
    private var mLocationManager : LocationManager? = null
    private var mLocationRequest : LocationRequest? = null

    init {
        mLocationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        mSettingClient = LocationServices.getSettingsClient(mContext)
        mLocationRequest = LocationRequest.create()
        mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest?.interval = 1000
        mLocationRequest?.fastestInterval = 50


        if (mLocationRequest != null)
        {
            val builder : LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
            builder.addLocationRequest(mLocationRequest!!)
            mLocationSettingsRequest = builder.build()
        }

    }


    fun turnOnGPS() {
        if (mLocationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == false)
        {
            mSettingClient?.checkLocationSettings(mLocationSettingsRequest!!)
                ?.addOnSuccessListener {
                    println("turnOnGPS : Already Enabled")
                }
                ?.addOnFailureListener{
                    if ((it as ApiException).statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED)
                    {
                        try
                        {
                            val resolvableApiException = it as ResolvableApiException
                            resolvableApiException.startResolutionForResult(mContext as Activity,
                                1001
                            )
                        }
                        catch (e : Exception)
                        {
                            println("turnOnGPS : Fail")
                        }
                    }
                    else
                    {
                        if ((it as ApiException).statusCode == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE)
                        {
                            println("turnOnGPS : Location settings are inadeguate fixed here. Fix in settings")
                        }
                    }
                }
        }

    }

}
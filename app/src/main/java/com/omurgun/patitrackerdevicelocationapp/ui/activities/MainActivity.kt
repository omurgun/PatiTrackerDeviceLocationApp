package com.omurgun.patitrackerdevicelocationapp.ui.activities

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.omurgun.patitrackerdevicelocationapp.data.models.request.RequestData
import com.omurgun.patitrackerdevicelocationapp.data.models.request.RequestDeviceData
import com.omurgun.patitrackerdevicelocationapp.databinding.ActivityMainBinding
import com.omurgun.patitrackerdevicelocationapp.service.ForegroundLocationService
import com.omurgun.patitrackerdevicelocationapp.ui.viewModels.MainViewModel
import com.omurgun.patitrackerdevicelocationapp.util.ResultData
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var locationPermissionState : LocationPermissionState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        locationPermissionState = LocationPermissionState(this) {
            if (it.hasPermission()) {
                mainViewModel.toggleLocationUpdates()
            }
        }


        val currentDate: String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.s", Locale.getDefault()).format(
            Date()
        )
       /* sendData(RequestData("11111111", listOf(
            RequestDeviceData(33.0,32.0,100.0,currentDate),
            RequestDeviceData(33.0,32.0,100.0,currentDate)
        )))*/

        binding.startButton.setOnClickListener {
            locationPermissionState.requestPermissions()

            binding.startButton.isEnabled = false
            binding.stopButton.isEnabled = true
        }

        binding.stopButton.setOnClickListener {
            locationPermissionState.requestPermissions()


            binding.startButton.isEnabled = true
            binding.stopButton.isEnabled = false
        }
    }

    override fun onStart() {
        super.onStart()
        val serviceIntent = Intent(this, ForegroundLocationService::class.java)
        bindService(serviceIntent, mainViewModel, BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(mainViewModel)
    }

    private fun sendData(requestData: RequestData){
        val data = mainViewModel.sendDataFromAPI(requestData)

        data.observe(this) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")

                }
                is ResultData.Success -> {

                    println("Success")
                    println("data : ${it.data}")


                }
                is ResultData.Exception -> {
                    println("Exception")


                }
            }
        }

    }

}
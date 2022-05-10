package com.omurgun.patitrackerdevicelocationapp.ui.activities

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.omurgun.patitrackerdevicelocationapp.data.models.request.RequestData
import com.omurgun.patitrackerdevicelocationapp.data.models.request.RequestDeviceData
import com.omurgun.patitrackerdevicelocationapp.databinding.ActivityMainBinding
import com.omurgun.patitrackerdevicelocationapp.ui.viewModels.MainViewModel
import com.omurgun.patitrackerdevicelocationapp.util.ResultData
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentDate: String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.s", Locale.getDefault()).format(
            Date()
        )
        sendData(RequestData("11111111", listOf(
            RequestDeviceData(33.0,32.0,100.0,currentDate),
            RequestDeviceData(33.0,32.0,100.0,currentDate)
        )))
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
package com.omurgun.patitrackerdevicelocationapp.util.timer

import android.app.Activity
import android.content.Intent


class TimerHelper(private val activity: Activity,private val timerServiceIntent : Intent)
{

    private var timerStarted = false
    private var dataSendTime = 120.0

    fun resetTimer()
    {
        if (timerStarted)
            stopTimer()
    }

    fun startTimer(sendTime : Double)
    {
        if (!timerStarted)
        {
            dataSendTime = sendTime
            timerServiceIntent.putExtra(TimerService.TIME_EXTRA, dataSendTime)
            activity.startService(timerServiceIntent)
            timerStarted = true
        }

    }

    private fun stopTimer()
    {
        activity.stopService(timerServiceIntent)
        timerStarted = false
    }




}
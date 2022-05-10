package com.omurgun.patitrackerdevicelocationapp.util.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter


class TimerBroadCastReceiver(private val  timerCallBack : TimerCallBack) : BroadcastReceiver() {

    private var isRegistered = false

    override fun onReceive(context: Context?, intent: Intent) {
        timerCallBack.tickTimer(intent)
    }

    fun register(context: Context) {
        if (!isRegistered) {
            context.registerReceiver(this, IntentFilter(TimerService.TIMER_UPDATED))
            isRegistered = true
        }
    }

    fun unregister(context: Context) {
        if (isRegistered) {
            context.unregisterReceiver(this)
            isRegistered = false
        }
    }



}
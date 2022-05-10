package com.omurgun.patitrackerdevicelocationapp.util.timer

import android.content.Intent

interface TimerCallBack {
    fun tickTimer(intent: Intent)
}
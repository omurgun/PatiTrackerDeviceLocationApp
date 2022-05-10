package com.omurgun.patitrackerdevicelocationapp.util


sealed class ResultData<out T> {
    data class Success<out T>(val data: T? = null): ResultData<T>()
    data class Loading(val nothing: Nothing? = null): ResultData<Nothing>()
    data class Exception(val message : String): ResultData<Nothing>()
}
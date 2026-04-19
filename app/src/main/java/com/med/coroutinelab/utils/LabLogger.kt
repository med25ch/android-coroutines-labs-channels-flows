package com.med.coroutinelab.utils

import android.util.Log

object LabLogger {
    fun log(tag: String, message: String) {
        Log.d("CoroutineLab[$tag]", message)
    }
}

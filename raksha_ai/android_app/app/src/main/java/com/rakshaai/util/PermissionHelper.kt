package com.rakshaai.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionHelper {
    private const val PERMISSION_REQUEST_CODE = 100

    val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.SEND_SMS,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.RECORD_AUDIO
    )

    fun hasPermissions(activity: Activity): Boolean {
        return requiredPermissions.all {
            ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(activity, requiredPermissions, PERMISSION_REQUEST_CODE)
    }
}

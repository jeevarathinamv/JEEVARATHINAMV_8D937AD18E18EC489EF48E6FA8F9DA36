package com.rakshaai.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class GPSHelper(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getLastLocation(): Task<Location> {
        return fusedLocationClient.lastLocation
    }
}

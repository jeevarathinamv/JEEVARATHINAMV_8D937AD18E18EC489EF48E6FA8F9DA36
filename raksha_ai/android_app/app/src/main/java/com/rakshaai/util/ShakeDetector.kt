package com.rakshaai.util

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class ShakeDetector(private val onShake: () -> Unit) : SensorEventListener {
    private var lastAcceleration = 0f
    private var currentAcceleration = 0f
    private var shakeThreshold = 12f

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        lastAcceleration = currentAcceleration
        currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
        val delta = currentAcceleration - lastAcceleration

        if (delta > shakeThreshold) {
            onShake()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}

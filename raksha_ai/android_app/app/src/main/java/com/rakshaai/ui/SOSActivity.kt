package com.rakshaai.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rakshaai.R
import com.rakshaai.util.EmergencyManager
import com.rakshaai.util.GPSHelper

class SOSActivity : AppCompatActivity() {
    private lateinit var emergencyManager: EmergencyManager
    private lateinit var gpsHelper: GPSHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sos)

        emergencyManager = EmergencyManager(this)
        gpsHelper = GPSHelper(this)

        val statusText = findViewById<TextView>(R.id.sosStatusText)
        val stopButton = findViewById<Button>(R.id.stopSOSButton)

        gpsHelper.getLastLocation().addOnSuccessListener { location ->
            if (location != null) {
                emergencyManager.triggerSOS(location.latitude, location.longitude)
                statusText.text = "SOS Triggered!\nSMS sent and GPS shared."
            } else {
                emergencyManager.triggerSOS(0.0, 0.0)
                statusText.text = "SOS Triggered!\nLocation unavailable but SMS sent."
            }
        }

        stopButton.setOnClickListener {
            emergencyManager.stopAlarm()
            finish()
        }

        // Call primary contact after a short delay
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            emergencyManager.callPrimaryContact()
        }, 3000)
    }
}

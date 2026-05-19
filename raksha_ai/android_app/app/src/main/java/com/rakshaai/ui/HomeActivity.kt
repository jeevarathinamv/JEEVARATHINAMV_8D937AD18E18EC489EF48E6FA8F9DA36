package com.rakshaai.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rakshaai.databinding.ActivityHomeBinding
import com.rakshaai.service.SOSForegroundService
import com.rakshaai.utils.ShakeDetector

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var shakeDetector: ShakeDetector

    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.SEND_SMS,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.RECORD_AUDIO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, 101)
        } else {
            startSOSService()
        }

        binding.sosCard.setOnClickListener {
            triggerSOS()
        }

        binding.contactsBtn.setOnClickListener {
            startActivity(Intent(this, ContactsActivity::class.java))
        }

        binding.settingsBtn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.historyBtn.setOnClickListener {
            startActivity(Intent(this, AlertHistoryActivity::class.java))
        }

        // Initialize Shake Detector
        shakeDetector = ShakeDetector {
            triggerSOS()
        }
        shakeDetector.start(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && allPermissionsGranted()) {
            startSOSService()
        }
    }

    private fun startSOSService() {
        val serviceIntent = Intent(this, SOSForegroundService::class.java)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun triggerSOS() {
        val intent = Intent(this, SOSActivity::class.java)
        startActivity(intent)
    }
}

package com.rakshaai.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationServices
import com.rakshaai.databinding.ActivitySosBinding
import com.rakshaai.model.AlertHistory
import com.rakshaai.network.FirebaseManager
import com.rakshaai.utils.EmergencyActions

class SOSActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executeEmergencyActions()

        binding.cancelSosBtn.setOnClickListener {
            EmergencyActions.stopAlarm()
            finish()
        }
    }

    private fun executeEmergencyActions() {
        EmergencyActions.playAlarm(this)

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            val lat = location?.latitude ?: 0.0
            val lon = location?.longitude ?: 0.0
            val mapsLink = "https://www.google.com/maps/search/?api=1&query=$lat,$lon"
            val message = "EMERGENCY! I need help. My location: $mapsLink"

            // Save Alert to Firebase
            val userId = FirebaseManager.auth.currentUser?.uid ?: "anonymous"
            val alertId = FirebaseManager.alertsRef.push().key ?: ""
            val alert = AlertHistory(alertId, userId, System.currentTimeMillis(), lat, lon)
            FirebaseManager.alertsRef.child(alertId).setValue(alert)

            // Send SMS to contacts and make a call to the first one
            FirebaseManager.contactsRef.child(userId).get().addOnSuccessListener { snapshot ->
                var firstCallMade = false
                snapshot.children.forEach {
                    val phone = it.child("phoneNumber").value.toString()
                    EmergencyActions.sendSms(this, phone, message)

                    if (!firstCallMade) {
                        EmergencyActions.makeCall(this, phone)
                        firstCallMade = true
                    }
                }
            }
        }
    }
}

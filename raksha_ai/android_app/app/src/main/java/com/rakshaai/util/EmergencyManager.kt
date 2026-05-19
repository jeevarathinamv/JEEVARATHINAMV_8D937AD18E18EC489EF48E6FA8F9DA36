package com.rakshaai.util

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.telephony.SmsManager
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rakshaai.model.Alert
import com.rakshaai.model.Contact

class EmergencyManager(private val context: Context) {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var mediaPlayer: MediaPlayer? = null

    fun triggerSOS(latitude: Double, longitude: Double) {
        playAlarm()
        saveAlertToFirebase(latitude, longitude)
        sendSMSToContacts(latitude, longitude)
        // callPrimaryContact() // This would usually be triggered by the user or after a delay
    }

    private fun playAlarm() {
        try {
            // Using a default ringtone or alarm sound if no specific resource is provided
            val alert = android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI
            mediaPlayer = MediaPlayer.create(context, alert)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        } catch (e: Exception) {
            Log.e("EmergencyManager", "Error playing alarm", e)
        }
    }

    fun stopAlarm() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun saveAlertToFirebase(lat: Double, lng: Double) {
        val userId = auth.currentUser?.uid ?: "anonymous"
        val alertId = db.collection("alerts").document().id
        val alert = Alert(alertId, userId, System.currentTimeMillis(), lat, lng)
        db.collection("alerts").document(alertId).set(alert)
    }

    private fun sendSMSToContacts(lat: Double, lng: Double) {
        val userId = auth.currentUser?.uid ?: return
        val message = "I am in danger! My location: http://maps.google.com/maps?q=$lat,$lng"

        db.collection("users").document(userId).collection("contacts").get()
            .addOnSuccessListener { documents ->
                val smsManager = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    context.getSystemService(SmsManager::class.java)
                } else {
                    SmsManager.getDefault()
                }
                for (document in documents) {
                    val contact = document.toObject(Contact::class.java)
                    try {
                        smsManager.sendTextMessage(contact.phoneNumber, null, message, null, null)
                    } catch (e: Exception) {
                        Log.e("EmergencyManager", "Failed to send SMS to ${contact.phoneNumber}", e)
                    }
                }
            }
    }

    fun callPrimaryContact() {
        val userId = auth.currentUser?.uid ?: return
        db.collection("users").document(userId).collection("contacts").limit(1).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val contact = documents.documents[0].toObject(Contact::class.java)
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:${contact?.phoneNumber}")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
    }
}

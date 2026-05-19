package com.rakshaai.utils

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.telephony.SmsManager
import android.widget.Toast

object EmergencyActions {
    private var mediaPlayer: MediaPlayer? = null

    fun sendSms(context: Context, phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(context, "SMS Sent to $phoneNumber", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to send SMS", Toast.LENGTH_SHORT).show()
        }
    }

    fun makeCall(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phoneNumber")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun playAlarm(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, android.provider.Settings.System.DEFAULT_RINGTONE_URI)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }
    }

    fun stopAlarm() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

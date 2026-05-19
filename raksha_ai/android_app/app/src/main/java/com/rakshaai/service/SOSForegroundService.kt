package com.rakshaai.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.rakshaai.ui.SOSActivity
import java.util.*

class SOSForegroundService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var speechRecognizer: SpeechRecognizer
    private var isListening = false
    private var powerButtonReceiver: com.rakshaai.utils.PowerButtonReceiver? = null

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        startForegroundService()
        initSpeechRecognizer()
        registerPowerButtonReceiver()
    }

    private fun registerPowerButtonReceiver() {
        powerButtonReceiver = com.rakshaai.utils.PowerButtonReceiver {
            triggerSOS()
        }
        val filter = android.content.IntentFilter().apply {
            addAction(android.content.Intent.ACTION_SCREEN_OFF)
            addAction(android.content.Intent.ACTION_SCREEN_ON)
        }
        registerReceiver(powerButtonReceiver, filter)
    }

    private fun startForegroundService() {
        val channelId = "SOS_SERVICE_CHANNEL"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "SOS Protection Mode", NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("RAKSHA AI is Active")
            .setContentText("You are protected in the background")
            .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
            .build()

        startForeground(1, notification)
    }

    private fun initSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                if (isListening) speechRecognizer.startListening(intent)
            }
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                matches?.forEach {
                    if (it.contains("SOS Help", ignoreCase = true)) {
                        triggerSOS()
                    }
                }
                if (isListening) speechRecognizer.startListening(intent)
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        isListening = true
        speechRecognizer.startListening(intent)
    }

    private fun triggerSOS() {
        val intent = Intent(this, SOSActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isListening = false
        speechRecognizer.destroy()
        powerButtonReceiver?.let { unregisterReceiver(it) }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

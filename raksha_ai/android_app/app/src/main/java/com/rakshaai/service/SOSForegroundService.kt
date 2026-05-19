package com.rakshaai.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.os.IBinder
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.core.app.NotificationCompat
import android.content.IntentFilter
import com.rakshaai.R
import com.rakshaai.ui.SOSActivity
import com.rakshaai.util.PowerButtonReceiver
import com.rakshaai.util.ShakeDetector
import java.util.*

class SOSForegroundService : Service() {
    private var speechRecognizer: SpeechRecognizer? = null
    private var sensorManager: SensorManager? = null
    private var shakeDetector: ShakeDetector? = null
    private var powerButtonReceiver: PowerButtonReceiver? = null

    override fun onCreate() {
        super.onCreate()
        startForeground(1, createNotification())
        setupVoiceDetection()
        setupShakeDetection()
        setupPowerButtonDetection()
    }

    private fun createNotification(): Notification {
        val channelId = "SOS_SERVICE_CHANNEL"
        val channelName = "SOS Protection Mode"
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW))

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("RAKSHA AI Protection Active")
            .setContentText("Listening for 'SOS Help' or Shake")
            .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
            .build()
    }

    private fun setupVoiceDetection() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null) {
                    for (match in matches) {
                        if (match.lowercase().contains("sos help")) {
                            triggerSOS()
                        }
                    }
                }
                // Restart listening
                speechRecognizer?.startListening(intent)
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                Log.e("SOSService", "Speech Error: $error")
                if (error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY) {
                    speechRecognizer?.cancel()
                }
                // Try to restart listening after a short delay to avoid infinite loops
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    try { speechRecognizer?.startListening(intent) } catch(e: Exception) {}
                }, 2000)
            }
        })

        speechRecognizer?.startListening(intent)
    }

    private fun setupShakeDetection() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        shakeDetector = ShakeDetector {
            triggerSOS()
        }
        val accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager?.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    private fun setupPowerButtonDetection() {
        powerButtonReceiver = PowerButtonReceiver {
            triggerSOS()
        }
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        registerReceiver(powerButtonReceiver, filter)
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
        speechRecognizer?.destroy()
        sensorManager?.unregisterListener(shakeDetector)
        powerButtonReceiver?.let { unregisterReceiver(it) }
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

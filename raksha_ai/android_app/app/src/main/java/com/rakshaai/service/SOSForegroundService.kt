package com.rakshaai.service
import android.app.*
import android.content.Intent
import android.os.IBinder
class SOSForegroundService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? = null
}

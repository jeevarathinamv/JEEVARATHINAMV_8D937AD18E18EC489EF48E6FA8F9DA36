package com.rakshaai.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PowerButtonReceiver(private val onTrigger: () -> Unit) : BroadcastReceiver() {
    private var count = 0
    private var lastClickTime = 0L

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_SCREEN_OFF || intent?.action == Intent.ACTION_SCREEN_ON) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime < 1000) {
                count++
            } else {
                count = 1
            }
            lastClickTime = currentTime

            if (count >= 3) {
                onTrigger()
                count = 0
            }
        }
    }
}

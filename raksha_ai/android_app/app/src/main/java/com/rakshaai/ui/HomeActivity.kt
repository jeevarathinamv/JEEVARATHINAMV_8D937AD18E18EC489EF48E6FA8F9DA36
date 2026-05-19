package com.rakshaai.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rakshaai.R
import com.rakshaai.service.SOSForegroundService
import com.rakshaai.util.PermissionHelper

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (!PermissionHelper.hasPermissions(this)) {
            PermissionHelper.requestPermissions(this)
        }

        val sosButton = findViewById<Button>(R.id.sosButton)
        val contactsButton = findViewById<ImageButton>(R.id.contactsButton)
        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)
        val protectionToggle = findViewById<Button>(R.id.protectionToggle)

        sosButton.setOnClickListener {
            startActivity(Intent(this, SOSActivity::class.java))
        }

        contactsButton.setOnClickListener {
            startActivity(Intent(this, ContactsActivity::class.java))
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        protectionToggle.setOnClickListener {
            val serviceIntent = Intent(this, SOSForegroundService::class.java)
            ContextCompat.startForegroundService(this, serviceIntent)
        }
    }
}

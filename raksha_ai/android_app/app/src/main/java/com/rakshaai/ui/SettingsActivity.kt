package com.rakshaai.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rakshaai.databinding.ActivitySettingsBinding
import com.rakshaai.network.FirebaseManager

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutBtn.setOnClickListener {
            FirebaseManager.auth.signOut()
            finishAffinity()
            startActivity(android.content.Intent(this, LoginActivity::class.java))
        }
    }
}

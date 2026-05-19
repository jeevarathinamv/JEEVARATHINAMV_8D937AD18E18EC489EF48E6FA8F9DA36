package com.rakshaai.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rakshaai.databinding.ActivityLoginBinding
import com.rakshaai.network.FirebaseManager

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val email = binding.emailLayout.editText?.text.toString()
            val password = binding.passwordLayout.editText?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseManager.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.signupTxt.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}

package com.rakshaai.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rakshaai.databinding.ActivitySignupBinding
import com.rakshaai.model.User
import com.rakshaai.network.FirebaseManager

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {
            val fullName = binding.nameLayout.editText?.text.toString()
            val email = binding.emailLayout.editText?.text.toString()
            val phone = binding.phoneLayout.editText?.text.toString()
            val password = binding.passwordLayout.editText?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseManager.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = FirebaseManager.auth.currentUser?.uid ?: ""
                            val user = User(userId, fullName, email, phone)
                            FirebaseManager.usersRef.child(userId).setValue(user)

                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Signup Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}

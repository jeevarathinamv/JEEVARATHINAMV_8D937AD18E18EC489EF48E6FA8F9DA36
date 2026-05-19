package com.rakshaai.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rakshaai.databinding.ActivityContactsBinding
import com.rakshaai.model.EmergencyContact
import com.rakshaai.network.FirebaseManager

class ContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addContactBtn.setOnClickListener {
            val name = binding.contactNameLayout.editText?.text.toString()
            val phone = binding.contactPhoneLayout.editText?.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                val userId = FirebaseManager.auth.currentUser?.uid ?: return@setOnClickListener
                val contactId = FirebaseManager.contactsRef.child(userId).push().key ?: ""
                val contact = EmergencyContact(contactId, name, phone)
                FirebaseManager.contactsRef.child(userId).child(contactId).setValue(contact)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}

package com.rakshaai.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rakshaai.R
import com.rakshaai.model.Contact

class ContactsActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val addContactButton = findViewById<Button>(R.id.addContactButton)
        addContactButton.setOnClickListener {
            showAddContactDialog()
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.contactsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val userId = auth.currentUser?.uid ?: return
        db.collection("users").document(userId).collection("contacts")
            .addSnapshotListener { value, error ->
                if (error != null) return@addSnapshotListener
                val contacts = value?.toObjects(Contact::class.java) ?: emptyList()
                recyclerView.adapter = ContactAdapter(contacts)
            }
    }

    private fun showAddContactDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Emergency Contact")
        val view = layoutInflater.inflate(R.layout.dialog_add_contact, null)
        val nameInput = view.findViewById<EditText>(R.id.contactNameInput)
        val phoneInput = view.findViewById<EditText>(R.id.contactPhoneInput)

        builder.setView(view)
        builder.setPositiveButton("Add") { _, _ ->
            val name = nameInput.text.toString()
            val phone = phoneInput.text.toString()
            if (name.isNotEmpty() && phone.isNotEmpty()) {
                saveContact(name, phone)
            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    private fun saveContact(name: String, phone: String) {
        val userId = auth.currentUser?.uid ?: return
        val contactId = db.collection("users").document(userId).collection("contacts").document().id
        val contact = Contact(contactId, name, phone)
        db.collection("users").document(userId).collection("contacts").document(contactId).set(contact)
    }
}

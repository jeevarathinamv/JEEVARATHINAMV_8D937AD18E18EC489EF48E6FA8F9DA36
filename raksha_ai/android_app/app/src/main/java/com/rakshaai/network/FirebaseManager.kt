package com.rakshaai.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object FirebaseManager {
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    val usersRef = database.getReference("users")
    val contactsRef = database.getReference("contacts")
    val alertsRef = database.getReference("alerts")
}

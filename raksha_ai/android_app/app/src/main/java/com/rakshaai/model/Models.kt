package com.rakshaai.model

data class User(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = ""
)

data class Contact(
    val id: String = "",
    val name: String = "",
    val phoneNumber: String = ""
)

data class Alert(
    val id: String = "",
    val userId: String = "",
    val timestamp: Long = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val status: String = "ACTIVE"
)

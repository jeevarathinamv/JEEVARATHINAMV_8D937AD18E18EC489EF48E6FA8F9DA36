package com.rakshaai.model

data class User(
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = ""
)

data class EmergencyContact(
    val id: String = "",
    val name: String = "",
    val phoneNumber: String = ""
)

data class AlertHistory(
    val id: String = "",
    val userId: String = "",
    val timestamp: Long = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val status: String = "ACTIVE"
)

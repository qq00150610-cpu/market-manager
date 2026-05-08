package com.example.marketmanager.data.models

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String = "",
    val role: String = "CONSUMER",
    @com.google.gson.annotations.SerializedName("registration_date") val registrationDate: String = "",
    @com.google.gson.annotations.SerializedName("last_login") val lastLogin: String? = null
)

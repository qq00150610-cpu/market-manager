package com.example.marketmanager.data.models

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val role: UserRole,
    val registrationDate: String,
    val lastLogin: String? = null,
    val avatarUrl: String? = null
)

enum class UserRole {
    ADMIN, MERCHANT, CONSUMER
}
package com.example.marketmanager.data.models

data class Merchant(
    val id: String,
    val name: String,
    val owner: String,
    val phone: String,
    val address: String,
    val stallNumber: String,
    val category: String,
    val status: MerchantStatus,
    val registrationDate: String,
    val businessLicense: String? = null,
    val description: String? = null
)

enum class MerchantStatus {
    ACTIVE, INACTIVE, PENDING
}
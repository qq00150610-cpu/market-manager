package com.example.marketmanager.data.models

data class Merchant(
    val id: String,
    val name: String,
    val owner: String,
    val phone: String,
    val address: String,
    @com.google.gson.annotations.SerializedName("stall_number") val stallNumber: String,
    val category: String,
    val status: String,
    @com.google.gson.annotations.SerializedName("created_at") val registrationDate: String = "",
    @com.google.gson.annotations.SerializedName("business_license") val businessLicense: String? = null,
    val description: String? = null
)

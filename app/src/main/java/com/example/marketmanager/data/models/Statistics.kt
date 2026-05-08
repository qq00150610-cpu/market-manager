package com.example.marketmanager.data.models

data class Notification(
    val id: String,
    val type: String,
    val title: String,
    val content: String,
    @com.google.gson.annotations.SerializedName("is_read") val isRead: Boolean = false,
    @com.google.gson.annotations.SerializedName("created_at") val createdAt: String = ""
)

data class Promotion(
    val id: String,
    val title: String,
    val type: String,
    val description: String = "",
    @com.google.gson.annotations.SerializedName("discount_value") val discountValue: Double = 0.0,
    @com.google.gson.annotations.SerializedName("min_amount") val minAmount: Double = 0.0,
    @com.google.gson.annotations.SerializedName("start_time") val startTime: String = "",
    @com.google.gson.annotations.SerializedName("end_time") val endTime: String = "",
    val status: String = "ACTIVE",
    @com.google.gson.annotations.SerializedName("created_at") val createdAt: String = ""
)

// 市场（用于本地展示）
data class Market(
    val id: String,
    val name: String,
    val address: String,
    val description: String,
    val phone: String = "",
    val openingHours: String = "",
    val imageUrl: String? = null
)

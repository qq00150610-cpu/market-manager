package com.example.marketmanager.data.models

data class GroupBuy(
    val id: String,
    val title: String,
    @com.google.gson.annotations.SerializedName("merchant_id") val merchantId: String,
    @com.google.gson.annotations.SerializedName("product_id") val productId: String,
    @com.google.gson.annotations.SerializedName("original_price") val originalPrice: Double,
    @com.google.gson.annotations.SerializedName("group_price") val groupPrice: Double,
    @com.google.gson.annotations.SerializedName("min_participants") val minParticipants: Int,
    @com.google.gson.annotations.SerializedName("current_participants") val currentParticipants: Int = 0,
    @com.google.gson.annotations.SerializedName("start_time") val startTime: String,
    @com.google.gson.annotations.SerializedName("end_time") val endTime: String,
    val status: String = "ACTIVE",
    @com.google.gson.annotations.SerializedName("created_at") val createdAt: String = ""
)

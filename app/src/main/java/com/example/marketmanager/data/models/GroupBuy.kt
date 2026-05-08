package com.example.marketmanager.data.models

data class GroupBuy(
    val id: String,
    val title: String,
    val merchantId: String,
    val productId: String,
    val originalPrice: Double,
    val groupPrice: Double,
    val minParticipants: Int,
    val currentParticipants: Int,
    val startTime: String,
    val endTime: String,
    val status: GroupBuyStatus,
    val description: String? = null,
    val imageUrl: String? = null
)

enum class GroupBuyStatus {
    UPCOMING, ACTIVE, ENDED, CANCELLED
}
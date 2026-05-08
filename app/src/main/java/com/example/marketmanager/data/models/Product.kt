package com.example.marketmanager.data.models

data class Product(
    val id: String,
    val name: String,
    @com.google.gson.annotations.SerializedName("merchant_id") val merchantId: String,
    val category: String,
    val price: Double,
    val unit: String,
    val stock: Int,
    val description: String? = null,
    @com.google.gson.annotations.SerializedName("image_url") val imageUrl: String? = null,
    val status: String = "AVAILABLE"
)

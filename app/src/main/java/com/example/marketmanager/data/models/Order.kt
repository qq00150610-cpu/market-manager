package com.example.marketmanager.data.models

data class Order(
    val id: String,
    @com.google.gson.annotations.SerializedName("user_id") val userId: String,
    @com.google.gson.annotations.SerializedName("merchant_id") val merchantId: String,
    @com.google.gson.annotations.SerializedName("total_amount") val totalAmount: Double,
    val status: String,
    @com.google.gson.annotations.SerializedName("order_date") val orderDate: String,
    @com.google.gson.annotations.SerializedName("delivery_date") val deliveryDate: String? = null,
    @com.google.gson.annotations.SerializedName("payment_method") val paymentMethod: String? = null,
    val notes: String? = null
)

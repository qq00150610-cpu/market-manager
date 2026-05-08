package com.example.marketmanager.data.models

data class Order(
    val id: String,
    val userId: String,
    val merchantId: String,
    val products: List<OrderItem>,
    val totalAmount: Double,
    val status: OrderStatus,
    val orderDate: String,
    val deliveryDate: String? = null,
    val paymentMethod: String? = null,
    val notes: String? = null
)

data class OrderItem(
    val productId: String,
    val productName: String,
    val quantity: Int,
    val unitPrice: Double,
    val subtotal: Double
)

enum class OrderStatus {
    PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}
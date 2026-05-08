package com.example.marketmanager.data.models

data class Product(
    val id: String,
    val name: String,
    val merchantId: String,
    val category: String,
    val price: Double,
    val unit: String,
    val stock: Int,
    val description: String? = null,
    val imageUrl: String? = null,
    val status: ProductStatus = ProductStatus.AVAILABLE
)

enum class ProductStatus {
    AVAILABLE, OUT_OF_STOCK, DISCONTINUED
}
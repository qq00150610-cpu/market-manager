package com.example.marketmanager.data.api

import com.example.marketmanager.data.models.*
import retrofit2.Response
import retrofit2.http.*

// 登录/注册请求体
data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val password: String)

// 通用响应
data class AuthResponse(val token: String, val user: UserResponse)
data class UserResponse(val id: String, val name: String, val email: String, val role: String)
data class SimpleResponse(val success: Boolean, val id: String? = null)
data class StatusUpdateRequest(val status: String)

// API接口
interface ApiService {

    // 认证
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    // 商户
    @GET("api/merchants")
    suspend fun getMerchants(): Response<List<Merchant>>

    @POST("api/merchants")
    suspend fun createMerchant(@Body body: Map<String, String>): Response<SimpleResponse>

    @PUT("api/merchants/{id}/status")
    suspend fun updateMerchantStatus(@Path("id") id: String, @Body body: StatusUpdateRequest): Response<SimpleResponse>

    // 商品
    @GET("api/products")
    suspend fun getProducts(): Response<List<Product>>

    @POST("api/products")
    suspend fun createProduct(@Body body: Map<String, Any>): Response<SimpleResponse>

    // 订单
    @GET("api/orders")
    suspend fun getOrders(): Response<List<Order>>

    @PUT("api/orders/{id}/status")
    suspend fun updateOrderStatus(@Path("id") id: String, @Body body: StatusUpdateRequest): Response<SimpleResponse>

    // 团购
    @GET("api/group-buys")
    suspend fun getGroupBuys(): Response<List<GroupBuy>>

    @POST("api/group-buys")
    suspend fun createGroupBuy(@Body body: Map<String, Any>): Response<SimpleResponse>

    // 用户
    @GET("api/users")
    suspend fun getUsers(): Response<List<User>>

    // 通知
    @GET("api/notifications")
    suspend fun getNotifications(): Response<List<Notification>>

    @PUT("api/notifications/{id}/read")
    suspend fun markNotificationRead(@Path("id") id: String): Response<SimpleResponse>

    @PUT("api/notifications/read-all")
    suspend fun markAllNotificationsRead(): Response<SimpleResponse>

    // 促销
    @GET("api/promotions")
    suspend fun getPromotions(): Response<List<Promotion>>

    // 统计
    @GET("api/stats/overview")
    suspend fun getStatsOverview(): Response<StatsOverviewResponse>
}

// 统计响应
data class StatsOverviewResponse(
    val merchants: Int,
    val activeMerchants: Int,
    val products: Int,
    val orders: Int,
    val todayOrders: Int,
    val totalRevenue: String,
    val todayRevenue: String,
    val pendingOrders: Int,
    val users: Int,
    val groupBuys: Int
)

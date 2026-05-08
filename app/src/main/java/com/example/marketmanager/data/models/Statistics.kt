package com.example.marketmanager.data.models

/**
 * 统计数据模型
 */
data class DailyStats(
    val date: String,
    val totalOrders: Int,
    val totalIncome: Double,
    val activeMerchants: Int,
    val totalProducts: Int
)

data class RevenueStats(
    val dailyRevenue: List<DailyStats>,
    val weeklyRevenue: List<DailyStats>,
    val monthlyRevenue: List<DailyStats>,
    val totalRevenue: Double,
    val averageDailyRevenue: Double,
    val revenueGrowth: Double // 百分比
)

data class MerchantStats(
    val totalMerchants: Int,
    val activeMerchants: Int,
    val inactiveMerchants: Int,
    val pendingMerchants: Int,
    val merchantGrowth: Double,
    val topCategories: List<CategoryStat>
)

data class CategoryStat(
    val category: String,
    val count: Int,
    val percentage: Double
)

data class ProductStats(
    val totalProducts: Int,
    val topSellingProducts: List<ProductSalesStat>,
    val categoryDistribution: List<CategoryStat>
)

data class ProductSalesStat(
    val productName: String,
    val merchantName: String,
    val totalSales: Int,
    val totalRevenue: Double
)

/**
 * 消息通知模型
 */
data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val timestamp: String,
    val isRead: Boolean,
    val relatedId: String? = null
)

enum class NotificationType {
    ORDER,       // 订单通知
    MERCHANT,    // 商户通知
    PROMOTION,   // 促销通知
    SYSTEM,      // 系统通知
    PAYMENT      // 支付通知
}

/**
 * 营销工具模型
 */
data class Promotion(
    val id: String,
    val title: String,
    val description: String,
    val type: PromotionType,
    val startTime: String,
    val endTime: String,
    val status: PromotionStatus,
    val discount: Double? = null,
    val minAmount: Double? = null,
    val maxDiscount: Double? = null,
    val applicableMerchants: List<String>? = null,
    val usageCount: Int = 0,
    val maxUsage: Int? = null
)

enum class PromotionType {
    COUPON,
    FLASH_SALE,
    FULL_REDUCTION,
    NEW_USER,
    MERCHANT_SPECIAL
}

enum class PromotionStatus {
    ACTIVE,
    SCHEDULED,
    EXPIRED,
    DISABLED
}

/**
 * 市场模型（支持多市场）
 */
data class Market(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val operatingHours: String,
    val totalStalls: Int,
    val occupiedStalls: Int,
    val status: MarketStatus,
    val description: String? = null,
    val imageUrl: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)

enum class MarketStatus {
    ACTIVE,
    INACTIVE,
    MAINTENANCE
}

/**
 * 导出数据模型
 */
data class ExportData(
    val merchants: List<Merchant>,
    val products: List<Product>,
    val orders: List<Order>,
    val users: List<User>,
    val revenue: RevenueStats,
    val exportDate: String,
    val exportFormat: ExportFormat
)

enum class ExportFormat {
    CSV,
    EXCEL,
    PDF
}

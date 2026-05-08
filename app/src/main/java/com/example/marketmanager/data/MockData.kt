package com.example.marketmanager.data

import com.example.marketmanager.data.models.*

object MockData {
    val merchants = listOf(
        Merchant(
            id = "1",
            name = "新鲜蔬菜店",
            owner = "张三",
            phone = "13800138001",
            address = "农贸市场A区1号",
            stallNumber = "A-001",
            category = "蔬菜",
            status = MerchantStatus.ACTIVE,
            registrationDate = "2024-01-15",
            businessLicense = "91110108MA01B1234",
            description = "提供新鲜有机蔬菜"
        ),
        Merchant(
            id = "2",
            name = "优质水果铺",
            owner = "李四",
            phone = "13800138002",
            address = "农贸市场B区5号",
            stallNumber = "B-005",
            category = "水果",
            status = MerchantStatus.ACTIVE,
            registrationDate = "2024-02-20",
            businessLicense = "91110108MA01B5678",
            description = "进口水果，品质保证"
        ),
        Merchant(
            id = "3",
            name = "老王肉铺",
            owner = "王五",
            phone = "13800138003",
            address = "农贸市场C区10号",
            stallNumber = "C-010",
            category = "肉类",
            status = MerchantStatus.PENDING,
            registrationDate = "2024-03-10",
            businessLicense = null,
            description = "新鲜猪肉、牛肉"
        )
    )

    val products = listOf(
        Product(
            id = "1",
            name = "有机西红柿",
            merchantId = "1",
            category = "蔬菜",
            price = 8.5,
            unit = "斤",
            stock = 100,
            description = "有机种植，无农药"
        ),
        Product(
            id = "2",
            name = "进口车厘子",
            merchantId = "2",
            category = "水果",
            price = 89.9,
            unit = "斤",
            stock = 50,
            description = "智利进口，甜度高"
        ),
        Product(
            id = "3",
            name = "五花肉",
            merchantId = "3",
            category = "肉类",
            price = 25.0,
            unit = "斤",
            stock = 80,
            description = "新鲜五花肉，适合红烧"
        )
    )

    val users = listOf(
        User(
            id = "1",
            name = "管理员",
            email = "admin@market.com",
            phone = "13900139000",
            role = UserRole.ADMIN,
            registrationDate = "2024-01-01"
        ),
        User(
            id = "2",
            name = "张三",
            email = "zhangsan@example.com",
            phone = "13800138001",
            role = UserRole.MERCHANT,
            registrationDate = "2024-01-15"
        ),
        User(
            id = "3",
            name = "消费者A",
            email = "consumer@example.com",
            phone = "13700137000",
            role = UserRole.CONSUMER,
            registrationDate = "2024-02-01"
        )
    )

    val orders = listOf(
        Order(
            id = "1",
            userId = "3",
            merchantId = "1",
            products = listOf(
                OrderItem(
                    productId = "1",
                    productName = "有机西红柿",
                    quantity = 2,
                    unitPrice = 8.5,
                    subtotal = 17.0
                )
            ),
            totalAmount = 17.0,
            status = OrderStatus.DELIVERED,
            orderDate = "2024-03-15 10:30",
            deliveryDate = "2024-03-15 14:00",
            paymentMethod = "微信支付"
        ),
        Order(
            id = "2",
            userId = "3",
            merchantId = "2",
            products = listOf(
                OrderItem(
                    productId = "2",
                    productName = "进口车厘子",
                    quantity = 1,
                    unitPrice = 89.9,
                    subtotal = 89.9
                )
            ),
            totalAmount = 89.9,
            status = OrderStatus.PROCESSING,
            orderDate = "2024-03-16 09:15",
            paymentMethod = "支付宝"
        )
    )

    val groupBuys = listOf(
        GroupBuy(
            id = "1",
            title = "有机蔬菜团购",
            merchantId = "1",
            productId = "1",
            originalPrice = 8.5,
            groupPrice = 6.5,
            minParticipants = 10,
            currentParticipants = 8,
            startTime = "2024-03-20 00:00",
            endTime = "2024-03-25 23:59",
            status = GroupBuyStatus.ACTIVE,
            description = "有机西红柿团购，满10人享团购价"
        ),
        GroupBuy(
            id = "2",
            title = "进口车厘子特价",
            merchantId = "2",
            productId = "2",
            originalPrice = 89.9,
            groupPrice = 69.9,
            minParticipants = 5,
            currentParticipants = 3,
            startTime = "2024-03-22 00:00",
            endTime = "2024-03-28 23:59",
            status = GroupBuyStatus.UPCOMING,
            description = "进口车厘子团购，限时特价"
        )
    )

    // 消息通知
    val notifications = listOf(
        Notification(
            id = "1",
            title = "新订单通知",
            message = "消费者A下单购买了有机西红柿，请及时处理",
            type = NotificationType.ORDER,
            timestamp = "2024-03-16 09:15",
            isRead = false,
            relatedId = "2"
        ),
        Notification(
            id = "2",
            title = "商户入驻审核",
            message = "老王肉铺的入驻申请已提交，请尽快审核",
            type = NotificationType.MERCHANT,
            timestamp = "2024-03-15 14:30",
            isRead = false,
            relatedId = "3"
        ),
        Notification(
            id = "3",
            title = "团购活动即将结束",
            message = "有机蔬菜团购活动还剩3天结束，当前参与人数8/10",
            type = NotificationType.PROMOTION,
            timestamp = "2024-03-22 10:00",
            isRead = true,
            relatedId = "1"
        ),
        Notification(
            id = "4",
            title = "系统维护通知",
            message = "系统将于2024-03-30 02:00-04:00进行例行维护",
            type = NotificationType.SYSTEM,
            timestamp = "2024-03-18 08:00",
            isRead = true
        ),
        Notification(
            id = "5",
            title = "支付成功通知",
            message = "订单#1的微信支付已到账 ¥17.00",
            type = NotificationType.PAYMENT,
            timestamp = "2024-03-15 10:35",
            isRead = true,
            relatedId = "1"
        )
    )

    // 统计数据
    val dailyStats = listOf(
        DailyStats("2026-05-01", 18, 2850.0, 3, 12),
        DailyStats("2026-05-02", 22, 3620.0, 3, 12),
        DailyStats("2026-05-03", 15, 2100.0, 3, 12),
        DailyStats("2026-05-04", 25, 4200.0, 3, 12),
        DailyStats("2026-05-05", 20, 3350.0, 3, 12),
        DailyStats("2026-05-06", 28, 4800.0, 3, 12),
        DailyStats("2026-05-07", 30, 5100.0, 3, 12)
    )

    val revenueStats = RevenueStats(
        dailyRevenue = dailyStats,
        weeklyRevenue = dailyStats,
        monthlyRevenue = dailyStats,
        totalRevenue = 26020.0,
        averageDailyRevenue = 3717.14,
        revenueGrowth = 15.3
    )

    val merchantStats = MerchantStats(
        totalMerchants = 3,
        activeMerchants = 2,
        inactiveMerchants = 0,
        pendingMerchants = 1,
        merchantGrowth = 12.5,
        topCategories = listOf(
            CategoryStat("蔬菜", 4, 33.3),
            CategoryStat("水果", 3, 25.0),
            CategoryStat("肉类", 3, 25.0),
            CategoryStat("海鲜", 1, 8.3),
            CategoryStat("干货", 1, 8.3)
        )
    )

    // 营销活动
    val promotions = listOf(
        Promotion(
            id = "1",
            title = "新用户首单立减",
            description = "新注册用户首次下单享受满50减20优惠",
            type = PromotionType.NEW_USER,
            startTime = "2026-05-01",
            endTime = "2026-06-30",
            status = PromotionStatus.ACTIVE,
            minAmount = 50.0,
            maxDiscount = 20.0,
            usageCount = 15,
            maxUsage = 100
        ),
        Promotion(
            id = "2",
            title = "夏季水果满减",
            description = "购买水果满100减15，满200减40",
            type = PromotionType.FULL_REDUCTION,
            startTime = "2026-05-01",
            endTime = "2026-05-31",
            status = PromotionStatus.ACTIVE,
            minAmount = 100.0,
            maxDiscount = 40.0,
            applicableMerchants = listOf("2"),
            usageCount = 8,
            maxUsage = 50
        ),
        Promotion(
            id = "3",
            title = "端午特惠9折",
            description = "端午节期间全场9折优惠",
            type = PromotionType.COUPON,
            startTime = "2026-06-01",
            endTime = "2026-06-10",
            status = PromotionStatus.SCHEDULED,
            discount = 0.9,
            usageCount = 0,
            maxUsage = 200
        ),
        Promotion(
            id = "4",
            title = "五一限时抢购",
            description = "五一劳动节限时抢购活动",
            type = PromotionType.FLASH_SALE,
            startTime = "2026-05-01",
            endTime = "2026-05-07",
            status = PromotionStatus.EXPIRED,
            discount = 0.8,
            usageCount = 45,
            maxUsage = 100
        )
    )

    // 多市场
    val markets = listOf(
        Market(
            id = "1",
            name = "城东农贸市场",
            address = "市城东区幸福路88号",
            phone = "0512-88886666",
            operatingHours = "06:00-18:00",
            totalStalls = 200,
            occupiedStalls = 180,
            status = MarketStatus.ACTIVE,
            description = "城东区最大的农贸市场，经营各类农副产品",
            latitude = 31.23,
            longitude = 121.47
        ),
        Market(
            id = "2",
            name = "城西生鲜市场",
            address = "市城西区人民路256号",
            phone = "0512-88887777",
            operatingHours = "06:30-17:30",
            totalStalls = 150,
            occupiedStalls = 120,
            status = MarketStatus.ACTIVE,
            description = "城西区现代化生鲜市场",
            latitude = 31.25,
            longitude = 121.45
        )
    )
}
package com.example.marketmanager.data

import com.example.marketmanager.data.models.*

object MockData {
    val merchants = listOf(
        Merchant("1", "新鲜蔬菜店", "张三", "13800138001", "农贸市场A区1号", "A-001", "蔬菜", "ACTIVE", "2024-01-15", "91110108MA01B1234", "提供新鲜有机蔬菜"),
        Merchant("2", "优质水果铺", "李四", "13800138002", "农贸市场B区5号", "B-005", "水果", "ACTIVE", "2024-02-20", "91110108MA01B5678", "进口水果，品质保证"),
        Merchant("3", "老王肉铺", "王五", "13800138003", "农贸市场C区10号", "C-010", "肉类", "PENDING", "2024-03-10", null, "新鲜猪肉、牛肉")
    )

    val products = listOf(
        Product("1", "有机西红柿", "1", "蔬菜", 8.5, "斤", 100, "有机种植，无农药", null, "AVAILABLE"),
        Product("2", "进口车厘子", "2", "水果", 89.9, "斤", 50, "智利进口，甜度高", null, "AVAILABLE"),
        Product("3", "五花肉", "3", "肉类", 25.0, "斤", 80, "新鲜五花肉，适合红烧", null, "AVAILABLE")
    )

    val users = listOf(
        User("1", "管理员", "admin@market.com", "13900139000", "ADMIN", "2024-01-01", null),
        User("2", "张三", "zhangsan@example.com", "13800138001", "MERCHANT", "2024-01-15", null),
        User("3", "消费者A", "consumer@example.com", "13700137000", "CONSUMER", "2024-02-01", null)
    )

    val orders = listOf(
        Order("1", "3", "1", 17.0, "DELIVERED", "2024-03-15 10:30", "2024-03-15 14:00", "微信支付", null),
        Order("2", "3", "2", 89.9, "PROCESSING", "2024-03-16 09:15", null, "支付宝", null)
    )

    val groupBuys = listOf(
        GroupBuy("1", "有机蔬菜团购", "1", "1", 8.5, 6.5, 10, 8, "2024-03-20 00:00", "2024-03-25 23:59", "ACTIVE", "2024-03-18"),
        GroupBuy("2", "进口车厘子特价", "2", "2", 89.9, 69.9, 5, 3, "2024-03-22 00:00", "2024-03-28 23:59", "ACTIVE", "2024-03-20")
    )

    val notifications = listOf(
        AppNotification("1", "ORDER", "新订单通知", "消费者A下单购买了有机西红柿，请及时处理", false, "2024-03-16 09:15"),
        AppNotification("2", "MERCHANT", "商户入驻审核", "老王肉铺的入驻申请已提交，请尽快审核", false, "2024-03-15 14:30"),
        AppNotification("3", "PROMOTION", "团购活动即将结束", "有机蔬菜团购活动还剩3天结束，当前参与人数8/10", true, "2024-03-22 10:00"),
        AppNotification("4", "SYSTEM", "系统维护通知", "系统将于2024-03-30 02:00-04:00进行例行维护", true, "2024-03-18 08:00"),
        AppNotification("5", "PAYMENT", "支付成功通知", "订单#1的微信支付已到账 ¥17.00", true, "2024-03-15 10:35")
    )

    val promotions = listOf(
        Promotion("1", "新用户首单立减", "NEW_USER", "新注册用户首次下单享受满50减20优惠", 20.0, 50.0, "2026-05-01", "2026-06-30", "ACTIVE", "2026-04-30"),
        Promotion("2", "夏季水果满减", "FULL_REDUCTION", "购买水果满100减15，满200减40", 40.0, 100.0, "2026-05-01", "2026-05-31", "ACTIVE", "2026-04-30"),
        Promotion("3", "端午特惠9折", "COUPON", "端午节期间全场9折优惠", 0.0, 0.0, "2026-06-01", "2026-06-10", "SCHEDULED", "2026-05-01"),
        Promotion("4", "五一限时抢购", "FLASH_SALE", "五一劳动节限时抢购活动", 0.0, 0.0, "2026-05-01", "2026-05-07", "EXPIRED", "2026-04-28")
    )

    val markets = listOf(
        Market("1", "城东农贸市场", "市城东区幸福路88号", "城东区最大的农贸市场", "0512-88886666", "06:00-18:00", null),
        Market("2", "城西生鲜市场", "市城西区人民路256号", "城西区现代化生鲜市场", "0512-88887777", "06:30-17:30", null)
    )
}

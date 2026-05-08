package com.example.marketmanager.data

import com.example.marketmanager.data.models.*

object MockData {
    val merchants = listOf(
        Merchant(
            id = "1",
            name = "新鲜蔬菜店",
            owner = "张三",
            phone: "13800138001",
            address: "农贸市场A区1号",
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
            phone: "13800138002",
            address: "农贸市场B区5号",
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
            phone: "13800138003",
            address: "农贸市场C区10号",
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
            phone: "13900139000",
            role = UserRole.ADMIN,
            registrationDate = "2024-01-01"
        ),
        User(
            id = "2",
            name = "张三",
            email = "zhangsan@example.com",
            phone: "13800138001",
            role = UserRole.MERCHANT,
            registrationDate = "2024-01-15"
        ),
        User(
            id = "3",
            name = "消费者A",
            email = "consumer@example.com",
            phone: "13700137000",
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
}
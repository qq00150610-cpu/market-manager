package com.example.marketmanager.ui.screens.promotion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.marketmanager.data.models.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionScreen(
    promotions: List<Promotion>,
    onBack: () -> Unit,
    onCreatePromotion: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("全部") }
    val filters = listOf("全部", "进行中", "已排期", "已结束", "已禁用")

    val filteredPromotions = when (selectedFilter) {
        "全部" -> promotions
        "进行中" -> promotions.filter { it.status == PromotionStatus.ACTIVE }
        "已排期" -> promotions.filter { it.status == PromotionStatus.SCHEDULED }
        "已结束" -> promotions.filter { it.status == PromotionStatus.EXPIRED }
        "已禁用" -> promotions.filter { it.status == PromotionStatus.DISABLED }
        else -> promotions
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("营销工具") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreatePromotion) {
                Icon(Icons.Default.Add, contentDescription = "创建活动")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 筛选器
            ScrollableTabRow(
                selectedTabIndex = filters.indexOf(selectedFilter),
                modifier = Modifier.fillMaxWidth()
            ) {
                filters.forEach { filter ->
                    Tab(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        text = { Text(filter) }
                    )
                }
            }

            if (filteredPromotions.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.LocalOffer,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "暂无营销活动",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "点击右下角按钮创建新活动",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredPromotions) { promotion ->
                        PromotionCard(promotion)
                    }
                }
            }
        }
    }
}

@Composable
fun PromotionCard(promotion: Promotion) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 活动类型标签
                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = getPromotionTypeName(promotion.type),
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = getPromotionTypeIcon(promotion.type),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                )

                // 状态标签
                Text(
                    text = getPromotionStatusName(promotion.status),
                    style = MaterialTheme.typography.labelSmall,
                    color = getPromotionStatusColor(promotion.status),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = promotion.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = promotion.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 活动详情
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "开始",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = promotion.startTime,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "结束",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = promotion.endTime,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            if (promotion.type == PromotionType.FULL_REDUCTION && promotion.minAmount != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "满¥%.2f 减¥%.2f".format(promotion.minAmount, promotion.maxDiscount ?: 0.0),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (promotion.type == PromotionType.COUPON && promotion.discount != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "折扣: %.0f折".format(promotion.discount * 10),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // 使用情况
            if (promotion.maxUsage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "使用进度",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${promotion.usageCount}/${promotion.maxUsage}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                @Suppress("DEPRECATION")
                LinearProgressIndicator(
                    progress = {
                        (promotion.usageCount.toFloat() / promotion.maxUsage)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .padding(top = 4.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

private fun getPromotionTypeName(type: PromotionType): String = when (type) {
    PromotionType.COUPON -> "优惠券"
    PromotionType.FLASH_SALE -> "限时抢购"
    PromotionType.FULL_REDUCTION -> "满减"
    PromotionType.NEW_USER -> "新用户优惠"
    PromotionType.MERCHANT_SPECIAL -> "商户特惠"
}

private fun getPromotionTypeIcon(type: PromotionType) = when (type) {
    PromotionType.COUPON -> Icons.Default.CardGiftcard
    PromotionType.FLASH_SALE -> Icons.Default.FlashOn
    PromotionType.FULL_REDUCTION -> Icons.Default.MoneyOff
    PromotionType.NEW_USER -> Icons.Default.PersonAdd
    PromotionType.MERCHANT_SPECIAL -> Icons.Default.Storefront
}

private fun getPromotionStatusName(status: PromotionStatus): String = when (status) {
    PromotionStatus.ACTIVE -> "进行中"
    PromotionStatus.SCHEDULED -> "已排期"
    PromotionStatus.EXPIRED -> "已结束"
    PromotionStatus.DISABLED -> "已禁用"
}

private fun getPromotionStatusColor(status: PromotionStatus): Color = when (status) {
    PromotionStatus.ACTIVE -> Color(0xFF4CAF50)
    PromotionStatus.SCHEDULED -> Color(0xFFFF9800)
    PromotionStatus.EXPIRED -> Color(0xFF9E9E9E)
    PromotionStatus.DISABLED -> Color(0xFFFF5252)
}

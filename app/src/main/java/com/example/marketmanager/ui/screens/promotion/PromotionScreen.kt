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
import androidx.compose.ui.unit.dp
import com.example.marketmanager.data.models.Promotion

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
        "进行中" -> promotions.filter { it.status == "ACTIVE" }
        "已排期" -> promotions.filter { it.status == "SCHEDULED" }
        "已结束" -> promotions.filter { it.status == "EXPIRED" }
        "已禁用" -> promotions.filter { it.status == "DISABLED" }
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
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
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
                        Icon(Icons.Default.LocalOffer, null, Modifier.size(64.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                        Spacer(Modifier.height(16.dp))
                        Text("暂无营销活动", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("点击右下角按钮创建新活动", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f))
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
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AssistChip(
                    onClick = {},
                    label = { Text(getPromotionTypeName(promotion.type), style = MaterialTheme.typography.labelSmall) },
                    leadingIcon = { Icon(getPromotionTypeIcon(promotion.type), null, Modifier.size(16.dp)) }
                )
                Text(getPromotionStatusName(promotion.status), style = MaterialTheme.typography.labelSmall, color = getPromotionStatusColor(promotion.status), fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(12.dp))
            Text(promotion.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(promotion.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("开始", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(promotion.startTime, style = MaterialTheme.typography.bodySmall)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("结束", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(promotion.endTime, style = MaterialTheme.typography.bodySmall)
                }
            }

            if (promotion.type == "FULL_REDUCTION" && promotion.minAmount > 0) {
                Spacer(Modifier.height(8.dp))
                Text("满¥${promotion.minAmount} 减¥${promotion.discountValue}",
                    style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

private fun getPromotionTypeName(type: String) = when (type) {
    "COUPON" -> "优惠券"
    "FLASH_SALE" -> "限时抢购"
    "FULL_REDUCTION" -> "满减"
    "NEW_USER" -> "新用户优惠"
    "MERCHANT_SPECIAL" -> "商户特惠"
    else -> type
}

private fun getPromotionTypeIcon(type: String) = when (type) {
    "COUPON" -> Icons.Default.CardGiftcard
    "FLASH_SALE" -> Icons.Default.FlashOn
    "FULL_REDUCTION" -> Icons.Default.MoneyOff
    "NEW_USER" -> Icons.Default.PersonAdd
    "MERCHANT_SPECIAL" -> Icons.Default.Storefront
    else -> Icons.Default.LocalOffer
}

private fun getPromotionStatusName(status: String) = when (status) {
    "ACTIVE" -> "进行中"
    "SCHEDULED" -> "已排期"
    "EXPIRED" -> "已结束"
    "DISABLED" -> "已禁用"
    else -> status
}

private fun getPromotionStatusColor(status: String): Color = when (status) {
    "ACTIVE" -> Color(0xFF4CAF50)
    "SCHEDULED" -> Color(0xFFFF9800)
    "EXPIRED" -> Color(0xFF9E9E9E)
    "DISABLED" -> Color(0xFFFF5252)
    else -> Color.Gray
}

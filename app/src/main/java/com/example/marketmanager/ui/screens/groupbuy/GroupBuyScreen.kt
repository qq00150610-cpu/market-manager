package com.example.marketmanager.ui.screens.groupbuy

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
import com.example.marketmanager.data.models.GroupBuy
import com.example.marketmanager.data.models.GroupBuyStatus
import com.example.marketmanager.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupBuyScreen(
    groupBuys: List<GroupBuy>,
    onAddGroupBuy: () -> Unit,
    onGroupBuyClick: (GroupBuy) -> Unit
) {
    var selectedStatus by remember { mutableStateOf<GroupBuyStatus?>(null) }
    
    val filteredGroupBuys = groupBuys.filter { groupBuy ->
        selectedStatus == null || groupBuy.status == selectedStatus
    }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddGroupBuy,
                containerColor = Primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "创建团购",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // 状态筛选
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedStatus == null,
                    onClick = { selectedStatus = null },
                    label = { Text("全部") }
                )
                FilterChip(
                    selected = selectedStatus == GroupBuyStatus.ACTIVE,
                    onClick = { selectedStatus = GroupBuyStatus.ACTIVE },
                    label = { Text("进行中") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF4CAF50)
                    )
                )
                FilterChip(
                    selected = selectedStatus == GroupBuyStatus.UPCOMING,
                    onClick = { selectedStatus = GroupBuyStatus.UPCOMING },
                    label = { Text("即将开始") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF2196F3)
                    )
                )
                FilterChip(
                    selected = selectedStatus == GroupBuyStatus.ENDED,
                    onClick = { selectedStatus = GroupBuyStatus.ENDED },
                    label = { Text("已结束") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF9E9E9E)
                    )
                )
            }
            
            // 统计信息
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatCard(
                    title = "总团购",
                    value = groupBuys.size.toString(),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                StatCard(
                    title = "进行中",
                    value = groupBuys.count { it.status == GroupBuyStatus.ACTIVE }.toString(),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                StatCard(
                    title = "即将开始",
                    value = groupBuys.count { it.status == GroupBuyStatus.UPCOMING }.toString(),
                    modifier = Modifier.weight(1f)
                )
            }
            
            // 团购列表
            if (filteredGroupBuys.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "没有找到团购活动",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(filteredGroupBuys) { groupBuy ->
                        GroupBuyCard(
                            groupBuy = groupBuy,
                            onClick = { onGroupBuyClick(groupBuy) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Primary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupBuyCard(
    groupBuy: GroupBuy,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = groupBuy.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                StatusChip(status = groupBuy.status)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "原价: ¥${groupBuy.originalPrice}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "团购价: ¥${groupBuy.groupPrice}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "进度: ${groupBuy.currentParticipants}/${groupBuy.minParticipants}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    
                    val progressValue = (groupBuy.currentParticipants.toFloat() / groupBuy.minParticipants).coerceIn(0f, 1f)
                    LinearProgressIndicator(
                        progress = progressValue,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(top = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "开始: ${groupBuy.startTime}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = "结束: ${groupBuy.endTime}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            if (groupBuy.description != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = groupBuy.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun StatusChip(status: GroupBuyStatus) {
    val (backgroundColor, text) = when (status) {
        GroupBuyStatus.ACTIVE -> Color(0xFF4CAF50) to "进行中"
        GroupBuyStatus.UPCOMING -> Color(0xFF2196F3) to "即将开始"
        GroupBuyStatus.ENDED -> Color(0xFF9E9E9E) to "已结束"
        GroupBuyStatus.CANCELLED -> Color(0xFFF44336) to "已取消"
    }
    
    Surface(
        shape = MaterialTheme.shapes.small,
        color = backgroundColor
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
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
import com.example.marketmanager.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupBuyScreen(
    groupBuys: List<GroupBuy>,
    onAddGroupBuy: () -> Unit,
    onGroupBuyClick: (GroupBuy) -> Unit
) {
    var selectedStatus by remember { mutableStateOf<String?>(null) }

    val filteredGroupBuys = groupBuys.filter {
        selectedStatus == null || it.status == selectedStatus
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("团购管理", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddGroupBuy, containerColor = Primary) {
                Icon(Icons.Default.Add, contentDescription = "创建团购", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(selected = selectedStatus == null, onClick = { selectedStatus = null }, label = { Text("全部") })
                FilterChip(selected = selectedStatus == "ACTIVE", onClick = { selectedStatus = "ACTIVE" }, label = { Text("进行中") })
                FilterChip(selected = selectedStatus == "UPCOMING" || selectedStatus == "SCHEDULED", onClick = { selectedStatus = "ACTIVE" }, label = { Text("即将开始") })
                FilterChip(selected = selectedStatus == "ENDED" || selectedStatus == "CANCELLED", onClick = { selectedStatus = "ENDED" }, label = { Text("已结束") })
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard("总活动", groupBuys.size.toString(), Modifier.weight(1f))
                StatCard("进行中", groupBuys.count { it.status == "ACTIVE" }.toString(), Modifier.weight(1f))
                StatCard("即将开始", groupBuys.count { it.status == "SCHEDULED" || it.status == "UPCOMING" }.toString(), Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filteredGroupBuys) { groupBuy ->
                    GroupBuyCard(groupBuy = groupBuy, onClick = { onGroupBuyClick(groupBuy) })
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Primary)
            Text(title, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GroupBuyCard(groupBuy: GroupBuy, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(groupBuy.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                StatusChip(groupBuy.status)
            }
            Spacer(Modifier.height(8.dp))
            Text("原价: ¥${groupBuy.originalPrice}  团购价: ¥${groupBuy.groupPrice}", style = MaterialTheme.typography.bodyMedium)
            Text("参与人数: ${groupBuy.currentParticipants}/${groupBuy.minParticipants}", style = MaterialTheme.typography.bodySmall)
            Text("时间: ${groupBuy.startTime.take(10)} ~ ${groupBuy.endTime.take(10)}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val (color, text) = when (status) {
        "ACTIVE" -> Color(0xFF4CAF50) to "进行中"
        "UPCOMING" -> Color(0xFF2196F3) to "即将开始"
        "SCHEDULED" -> Color(0xFF2196F3) to "即将开始"
        "ENDED" -> Color(0xFF9E9E9E) to "已结束"
        "CANCELLED" -> Color(0xFFF44336) to "已取消"
        else -> Color.Gray to status
    }
    Surface(color = color.copy(alpha = 0.15f), shape = MaterialTheme.shapes.small) {
        Text(text = text, color = color, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
    }
}

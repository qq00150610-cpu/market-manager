package com.example.marketmanager.ui.screens.order

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
import com.example.marketmanager.data.models.Order
import com.example.marketmanager.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    orders: List<Order>,
    onOrderClick: (Order) -> Unit,
    onUpdateStatus: (String, String) -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf<String?>(null) }

    val filteredOrders = orders.filter { order ->
        val matchesSearch = searchQuery.isEmpty() || order.id.contains(searchQuery)
        val matchesStatus = selectedStatus == null || order.status == selectedStatus
        matchesSearch && matchesStatus
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("订单管理", color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("搜索订单号...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(selected = selectedStatus == null, onClick = { selectedStatus = null }, label = { Text("全部") })
                FilterChip(selected = selectedStatus == "PENDING", onClick = { selectedStatus = "PENDING" }, label = { Text("待处理") })
                FilterChip(selected = selectedStatus == "CONFIRMED", onClick = { selectedStatus = "CONFIRMED" }, label = { Text("已确认") })
                FilterChip(selected = selectedStatus == "DELIVERED", onClick = { selectedStatus = "DELIVERED" }, label = { Text("已送达") })
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard("总订单", orders.size.toString(), Modifier.weight(1f))
                StatCard("待处理", orders.count { it.status == "PENDING" }.toString(), Modifier.weight(1f))
                StatCard("已送达", orders.count { it.status == "DELIVERED" }.toString(), Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filteredOrders) { order ->
                    OrderCard(
                        order = order,
                        onClick = { onOrderClick(order) },
                        onUpdateStatus = onUpdateStatus
                    )
                }
            }
        }
    }
}

@Composable
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
fun OrderCard(
    order: Order,
    onClick: () -> Unit,
    onUpdateStatus: (String, String) -> Unit
) {
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
                Text("订单 #${order.id.take(8)}", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                StatusChip(order.status)
            }
            Spacer(Modifier.height(8.dp))
            Text("金额: ¥${order.totalAmount}", style = MaterialTheme.typography.bodyMedium)
            Text("日期: ${order.orderDate}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))

            if (order.status == "PENDING") {
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { onUpdateStatus(order.id, "CONFIRMED") },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("确认订单")
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val (color, text) = when (status) {
        "PENDING" -> Color(0xFFFFC107) to "待处理"
        "CONFIRMED" -> Color(0xFF2196F3) to "已确认"
        "PROCESSING" -> Color(0xFFFF9800) to "处理中"
        "SHIPPED" -> Color(0xFF9C27B0) to "已发货"
        "DELIVERED" -> Color(0xFF4CAF50) to "已送达"
        "CANCELLED" -> Color(0xFFF44336) to "已取消"
        else -> Color.Gray to status
    }
    Surface(color = color.copy(alpha = 0.15f), shape = MaterialTheme.shapes.small) {
        Text(text = text, color = color, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
    }
}

package com.example.marketmanager.ui.screens.market

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
import com.example.marketmanager.data.models.Merchant
import com.example.marketmanager.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(
    merchants: List<Merchant>,
    onAddMerchant: () -> Unit,
    onMerchantClick: (Merchant) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf<String?>(null) }

    val filteredMerchants = merchants.filter { merchant ->
        val matchesSearch = searchQuery.isEmpty() ||
            merchant.name.contains(searchQuery, ignoreCase = true) ||
            merchant.owner.contains(searchQuery, ignoreCase = true)
        val matchesStatus = selectedStatus == null || merchant.status == selectedStatus
        matchesSearch && matchesStatus
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("市场管理", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddMerchant,
                containerColor = Primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "添加商户", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // 搜索框
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("搜索商户名称、负责人...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 状态筛选
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedStatus == null,
                    onClick = { selectedStatus = null },
                    label = { Text("全部") }
                )
                FilterChip(
                    selected = selectedStatus == "ACTIVE",
                    onClick = { selectedStatus = "ACTIVE" },
                    label = { Text("营业中") }
                )
                FilterChip(
                    selected = selectedStatus == "INACTIVE",
                    onClick = { selectedStatus = "INACTIVE" },
                    label = { Text("已关闭") }
                )
                FilterChip(
                    selected = selectedStatus == "PENDING",
                    onClick = { selectedStatus = "PENDING" },
                    label = { Text("待审核") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 统计卡片
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard("总商户", merchants.size.toString(), Modifier.weight(1f))
                StatCard("营业中", merchants.count { it.status == "ACTIVE" }.toString(), Modifier.weight(1f))
                StatCard("待审核", merchants.count { it.status == "PENDING" }.toString(), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 商户列表
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredMerchants) { merchant ->
                    MerchantCard(merchant = merchant, onClick = { onMerchantClick(merchant) })
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
            Text(text = value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Primary)
            Text(text = title, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        }
    }
}

@Composable
fun MerchantCard(merchant: Merchant, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Store, contentDescription = null, tint = Primary, modifier = Modifier.size(40.dp))
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(merchant.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("负责人: ${merchant.owner} | ${merchant.category}", style = MaterialTheme.typography.bodySmall)
                Text("摊位: ${merchant.stallNumber}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
            StatusChip(merchant.status)
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val (color, text) = when (status) {
        "ACTIVE" -> Color(0xFF4CAF50) to "营业中"
        "INACTIVE" -> Color(0xFFF44336) to "已关闭"
        "PENDING" -> Color(0xFFFFC107) to "待审核"
        else -> Color.Gray to status
    }
    Surface(color = color.copy(alpha = 0.15f), shape = MaterialTheme.shapes.small) {
        Text(text = text, color = color, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
    }
}

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
import com.example.marketmanager.data.models.MerchantStatus
import com.example.marketmanager.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(
    merchants: List<Merchant>,
    onAddMerchant: () -> Unit,
    onMerchantClick: (Merchant) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf<MerchantStatus?>(null) }
    
    val filteredMerchants = merchants.filter { merchant ->
        (searchQuery.isEmpty() || 
         merchant.name.contains(searchQuery, ignoreCase = true) ||
         merchant.owner.contains(searchQuery, ignoreCase = true)) &&
        (selectedStatus == null || merchant.status == selectedStatus)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 搜索栏
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("搜索商户") },
            placeholder = { Text("输入商户名称或负责人") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "搜索") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        // 状态筛选
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilterChip(
                selected = selectedStatus == null,
                onClick = { selectedStatus = null },
                label = { Text("全部") }
            )
            FilterChip(
                selected = selectedStatus == MerchantStatus.ACTIVE,
                onClick = { selectedStatus = MerchantStatus.ACTIVE },
                label = { Text("营业中") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF4CAF50)
                )
            )
            FilterChip(
                selected = selectedStatus == MerchantStatus.INACTIVE,
                onClick = { selectedStatus = MerchantStatus.INACTIVE },
                label = { Text("已关闭") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFF44336)
                )
            )
            FilterChip(
                selected = selectedStatus == MerchantStatus.PENDING,
                onClick = { selectedStatus = MerchantStatus.PENDING },
                label = { Text("待审核") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFFFC107)
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
                title = "总商户",
                value = merchants.size.toString(),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatCard(
                title = "营业中",
                value = merchants.count { it.status == MerchantStatus.ACTIVE }.toString(),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatCard(
                title = "待审核",
                value = merchants.count { it.status == MerchantStatus.PENDING }.toString(),
                modifier = Modifier.weight(1f)
            )
        }
        
        // 商户列表
        if (filteredMerchants.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "没有找到商户",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(filteredMerchants) { merchant ->
                    MerchantCard(
                        merchant = merchant,
                        onClick = { onMerchantClick(merchant) }
                    )
                }
            }
        }
        
        // 添加商户按钮
        FloatingActionButton(
            onClick = onAddMerchant,
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp),
            containerColor = Primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "添加商户",
                tint = Color.White
            )
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
fun MerchantCard(
    merchant: Merchant,
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
                    text = merchant.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                StatusChip(status = merchant.status)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "负责人: ${merchant.owner}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "摊位: ${merchant.stallNumber}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "分类: ${merchant.category}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            if (merchant.description != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = merchant.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun StatusChip(status: MerchantStatus) {
    val (backgroundColor, text) = when (status) {
        MerchantStatus.ACTIVE -> Color(0xFF4CAF50) to "营业中"
        MerchantStatus.INACTIVE -> Color(0xFFF44336) to "已关闭"
        MerchantStatus.PENDING -> Color(0xFFFFC107) to "待审核"
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
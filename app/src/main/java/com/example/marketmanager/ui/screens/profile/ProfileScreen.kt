package com.example.marketmanager.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.marketmanager.ui.theme.Primary

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onViewOrders: () -> Unit = {}
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // 用户信息卡片
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Primary
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "用户头像",
                    modifier = Modifier.size(64.dp),
                    tint = Color.White
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "管理员",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Text(
                    text = "admin@market.com",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.8f)
                )
                
                Text(
                    text = "农贸市场管理系统",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        
        // 统计信息
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatCard(
                title = "管理商户",
                value = "12",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatCard(
                title = "处理订单",
                value = "156",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatCard(
                title = "运行天数",
                value = "45",
                modifier = Modifier.weight(1f)
            )
        }
        
        // 功能菜单
        Text(
            text = "系统管理",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        MenuCard(
            icon = Icons.Default.Business,
            title = "商户管理",
            subtitle = "管理入驻商户信息",
            onClick = { /* 跳转到商户管理 */ }
        )
        
        MenuCard(
            icon = Icons.Default.ShoppingCart,
            title = "商品管理",
            description = "管理市场内商品",
            onClick = { /* 跳转到商品管理 */ }
        )
        
        MenuCard(
            icon = Icons.Default.Group,
            title = "团购管理",
            description = "管理团购活动",
            onClick = { /* 跳转到团购管理 */ }
        )
        
        MenuCard(
            icon = Icons.Default.Receipt,
            title = "订单管理",
            description = "查看和处理订单",
            onClick = onViewOrders
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "系统设置",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        MenuCard(
            icon = Icons.Default.Settings,
            title = "系统设置",
            description = "系统参数配置",
            onClick = { /* 跳转到系统设置 */ }
        )
        
        MenuCard(
            icon = Icons.Default.Notifications,
            title = "消息通知",
            description = "查看系统通知",
            onClick = { /* 跳转到消息通知 */ }
        )
        
        MenuCard(
            icon = Icons.Default.Help,
            title = "帮助中心",
            description = "使用帮助和常见问题",
            onClick = { /* 跳转到帮助中心 */ }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 退出登录按钮
        Button(
            onClick = { showLogoutDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF44336)
            )
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "退出登录",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "退出登录",
                fontWeight = FontWeight.Bold
            )
        }
        
        // 退出登录确认对话框
        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("确认退出") },
                text = { Text("确定要退出登录吗？") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showLogoutDialog = false
                            onLogout()
                        }
                    ) {
                        Text("确定", color = Color(0xFFF44336))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showLogoutDialog = false }
                    ) {
                        Text("取消")
                    }
                }
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
fun MenuCard(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    description: String? = null,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Primary,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                
                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "进入",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
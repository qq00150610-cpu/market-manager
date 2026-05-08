package com.example.marketmanager.ui.screens.notification

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
import com.example.marketmanager.data.models.Notification
import com.example.marketmanager.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    notifications: List<Notification>,
    onBack: () -> Unit,
    onMarkAsRead: (String) -> Unit,
    onMarkAllAsRead: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("消息通知", color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                actions = {
                    if (notifications.any { !it.isRead }) {
                        TextButton(onClick = onMarkAllAsRead) {
                            Text("全部已读", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
            )
        }
    ) { paddingValues ->
        if (notifications.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.NotificationsNone, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.Gray)
                    Spacer(Modifier.height(16.dp))
                    Text("暂无通知", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notifications) { notification ->
                    NotificationCard(
                        notification = notification,
                        onClick = { if (!notification.isRead) onMarkAsRead(notification.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationCard(
    notification: Notification,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (!notification.isRead) Primary.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = getNotificationIcon(notification.type),
                contentDescription = null,
                tint = getNotificationColor(notification.type),
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(notification.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    if (!notification.isRead) {
                        Surface(
                            color = Color.Red,
                            shape = MaterialTheme.shapes.extraSmall,
                            modifier = Modifier.size(8.dp)
                        ) {}
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(notification.content, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                if (notification.createdAt.isNotEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Text(notification.createdAt, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                }
            }
        }
    }
}

private fun getNotificationIcon(type: String) = when (type) {
    "ORDER" -> Icons.Default.ShoppingCart
    "MERCHANT" -> Icons.Default.Store
    "PROMOTION" -> Icons.Default.LocalOffer
    "SYSTEM" -> Icons.Default.Settings
    "PAYMENT" -> Icons.Default.Payment
    else -> Icons.Default.Notifications
}

private fun getNotificationColor(type: String) = when (type) {
    "ORDER" -> Color(0xFF2196F3)
    "MERCHANT" -> Color(0xFF4CAF50)
    "PROMOTION" -> Color(0xFFFF9800)
    "SYSTEM" -> Color(0xFF9C27B0)
    "PAYMENT" -> Color(0xFF00BCD4)
    else -> Color.Gray
}

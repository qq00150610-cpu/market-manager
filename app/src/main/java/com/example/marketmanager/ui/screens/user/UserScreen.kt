package com.example.marketmanager.ui.screens.user

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
import com.example.marketmanager.data.models.User
import com.example.marketmanager.data.models.UserRole
import com.example.marketmanager.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    users: List<User>,
    onAddUser: () -> Unit,
    onUserClick: (User) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf<UserRole?>(null) }
    
    val filteredUsers = users.filter { user ->
        (searchQuery.isEmpty() || 
         user.name.contains(searchQuery, ignoreCase = true) ||
         user.email.contains(searchQuery, ignoreCase = true)) &&
        (selectedRole == null || user.role == selectedRole)
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
            label = { Text("搜索用户") },
            placeholder = { Text("输入用户名或邮箱") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "搜索") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        // 角色筛选
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedRole == null,
                onClick = { selectedRole = null },
                label = { Text("全部") }
            )
            FilterChip(
                selected = selectedRole == UserRole.ADMIN,
                onClick = { selectedRole = UserRole.ADMIN },
                label = { Text("管理员") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFF44336)
                )
            )
            FilterChip(
                selected = selectedRole == UserRole.MERCHANT,
                onClick = { selectedRole = UserRole.MERCHANT },
                label = { Text("商户") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF4CAF50)
                )
            )
            FilterChip(
                selected = selectedRole == UserRole.CONSUMER,
                onClick = { selectedRole = UserRole.CONSUMER },
                label = { Text("消费者") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF2196F3)
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
                title = "总用户",
                value = users.size.toString(),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatCard(
                title = "管理员",
                value = users.count { it.role == UserRole.ADMIN }.toString(),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatCard(
                title = "商户",
                value = users.count { it.role == UserRole.MERCHANT }.toString(),
                modifier = Modifier.weight(1f)
            )
        }
        
        // 用户列表
        if (filteredUsers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "没有找到用户",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(filteredUsers) { user ->
                    UserCard(
                        user = user,
                        onClick = { onUserClick(user) }
                    )
                }
            }
        }
        
        // 添加用户按钮
        FloatingActionButton(
            onClick = onAddUser,
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp),
            containerColor = Primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "添加用户",
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
fun UserCard(
    user: User,
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
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                RoleChip(role = user.role)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "邮箱: ${user.email}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "手机: ${user.phone}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "注册时间: ${user.registrationDate}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            if (user.lastLogin != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "最后登录: ${user.lastLogin}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun RoleChip(role: UserRole) {
    val (backgroundColor, text) = when (role) {
        UserRole.ADMIN -> Color(0xFFF44336) to "管理员"
        UserRole.MERCHANT -> Color(0xFF4CAF50) to "商户"
        UserRole.CONSUMER -> Color(0xFF2196F3) to "消费者"
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
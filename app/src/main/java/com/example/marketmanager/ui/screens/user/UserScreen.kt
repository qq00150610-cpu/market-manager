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
import com.example.marketmanager.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    users: List<User>,
    onAddUser: () -> Unit,
    onUserClick: (User) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf<String?>(null) }

    val filteredUsers = users.filter { user ->
        val matchesSearch = searchQuery.isEmpty() ||
            user.name.contains(searchQuery, ignoreCase = true) ||
            user.email.contains(searchQuery, ignoreCase = true)
        val matchesRole = selectedRole == null || user.role == selectedRole
        matchesSearch && matchesRole
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("用户管理", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddUser, containerColor = Primary) {
                Icon(Icons.Default.Add, contentDescription = "添加用户", tint = Color.White)
            }
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
                placeholder = { Text("搜索用户名、邮箱...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(selected = selectedRole == null, onClick = { selectedRole = null }, label = { Text("全部") })
                FilterChip(selected = selectedRole == "ADMIN", onClick = { selectedRole = "ADMIN" }, label = { Text("管理员") })
                FilterChip(selected = selectedRole == "MERCHANT", onClick = { selectedRole = "MERCHANT" }, label = { Text("商户") })
                FilterChip(selected = selectedRole == "CONSUMER", onClick = { selectedRole = "CONSUMER" }, label = { Text("消费者") })
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard("总用户", users.size.toString(), Modifier.weight(1f))
                StatCard("管理员", users.count { it.role == "ADMIN" }.toString(), Modifier.weight(1f))
                StatCard("商户", users.count { it.role == "MERCHANT" }.toString(), Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filteredUsers) { user ->
                    UserCard(user = user, onClick = { onUserClick(user) })
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
fun UserCard(user: User, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Person, contentDescription = null, tint = Primary, modifier = Modifier.size(40.dp))
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(user.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(user.email, style = MaterialTheme.typography.bodySmall)
            }
            RoleChip(user.role)
        }
    }
}

@Composable
fun RoleChip(role: String) {
    val (color, text) = when (role) {
        "ADMIN" -> Color(0xFFF44336) to "管理员"
        "MERCHANT" -> Color(0xFF4CAF50) to "商户"
        "CONSUMER" -> Color(0xFF2196F3) to "消费者"
        else -> Color.Gray to role
    }
    Surface(color = color.copy(alpha = 0.15f), shape = MaterialTheme.shapes.small) {
        Text(text = text, color = color, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
    }
}

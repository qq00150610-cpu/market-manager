package com.example.marketmanager.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marketmanager.ui.theme.Primary
import com.example.marketmanager.viewmodel.MainViewModel
import com.example.marketmanager.ui.screens.market.MarketScreen
import com.example.marketmanager.ui.screens.merchant.MerchantScreen
import com.example.marketmanager.ui.screens.groupbuy.GroupBuyScreen
import com.example.marketmanager.ui.screens.profile.ProfileScreen
import com.example.marketmanager.ui.screens.user.UserScreen
import com.example.marketmanager.ui.screens.order.OrderScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onLogout: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf(0) }
    var showOrderScreen by remember { mutableStateOf(false) }
    
    val merchants by viewModel.merchants.collectAsState()
    val products by viewModel.products.collectAsState()
    val groupBuys by viewModel.groupBuys.collectAsState()
    val users by viewModel.users.collectAsState()
    
    val tabs = listOf(
        TabItem("首页", Icons.Default.Home),
        TabItem("市场", Icons.Default.Store),
        TabItem("商户", Icons.Default.Business),
        TabItem("团购", Icons.Default.Group),
        TabItem("用户", Icons.Default.People),
        TabItem("我的", Icons.Default.Person)
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "农贸市场管理系统",
                        color = MaterialTheme.colorScheme.onPrimary
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary
                ),
                actions = {
                    IconButton(onClick = { /* 搜索 */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "搜索",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(onClick = { /* 通知 */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "通知",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        icon = { Icon(tab.icon, contentDescription = tab.title) },
                        label = { Text(tab.title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (showOrderScreen) {
                OrderScreen(
                    orders = orders,
                    onOrderClick = { /* 订单详情 */ },
                    onUpdateStatus = { orderId, status ->
                        viewModel.updateOrderStatus(orderId, status)
                    },
                    onBack = { showOrderScreen = false }
                )
            } else {
                when (selectedTab) {
                    0 -> HomeScreen(viewModel)
                    1 -> MarketScreen(
                        merchants = merchants,
                        onAddMerchant = { /* 添加商户 */ },
                        onMerchantClick = { /* 商户详情 */ }
                    )
                    2 -> MerchantScreen(
                        products = products,
                        onAddProduct = { /* 添加商品 */ },
                        onProductClick = { /* 商品详情 */ }
                    )
                    3 -> GroupBuyScreen(
                        groupBuys = groupBuys,
                        onAddGroupBuy = { /* 创建团购 */ },
                        onGroupBuyClick = { /* 团购详情 */ }
                    )
                    4 -> UserScreen(
                        users = users,
                        onAddUser = { /* 添加用户 */ },
                        onUserClick = { /* 用户详情 */ }
                    )
                    5 -> ProfileScreen(
                        onLogout = onLogout,
                        onViewOrders = { showOrderScreen = true }
                    )
                }
            }
        }
    }
}

data class TabItem(
    val title: String,
    val icon: ImageVector
)

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val merchants by viewModel.merchants.collectAsState()
    val orders by viewModel.orders.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 欢迎信息
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Primary
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "欢迎回来！",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "今天有 ${viewModel.pendingOrders} 个新订单待处理",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        
        // 统计卡片
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatCard(
                title = "今日订单",
                value = viewModel.todayOrders.toString(),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatCard(
                title = "今日收入",
                value = "¥${String.format("%.2f", viewModel.todayIncome)}",
                modifier = Modifier.weight(1f)
            )
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatCard(
                title = "活跃商户",
                value = viewModel.activeMerchants.toString(),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatCard(
                title = "待处理",
                value = viewModel.pendingOrders.toString(),
                modifier = Modifier.weight(1f)
            )
        }
        
        // 快速操作
        Text(
            text = "快速操作",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuickActionButton(
                title = "添加商户",
                icon = Icons.Default.Business,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            QuickActionButton(
                title = "添加商品",
                icon = Icons.Default.ShoppingCart,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            QuickActionButton(
                title = "创建团购",
                icon = Icons.Default.Group,
                modifier = Modifier.weight(1f)
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
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = Primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun QuickActionButton(
    title: String,
    icon: ImageVector,
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
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Primary,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
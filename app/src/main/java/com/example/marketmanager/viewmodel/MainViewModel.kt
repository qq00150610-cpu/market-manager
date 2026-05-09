package com.example.marketmanager.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketmanager.data.MockData
import com.example.marketmanager.data.api.RetrofitClient
import com.example.marketmanager.data.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {

    // ============ 网络状态 ============
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun clearError() { _errorMessage.value = null }

    // ============ 数据 ============
    private val _merchants = MutableStateFlow(MockData.merchants)
    val merchants: StateFlow<List<Merchant>> = _merchants.asStateFlow()

    private val _products = MutableStateFlow(MockData.products)
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _users = MutableStateFlow(MockData.users)
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _orders = MutableStateFlow(MockData.orders)
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    private val _groupBuys = MutableStateFlow(MockData.groupBuys)
    val groupBuys: StateFlow<List<GroupBuy>> = _groupBuys.asStateFlow()

    private val _notifications = MutableStateFlow(MockData.notifications)
    val notifications: StateFlow<List<AppNotification>> = _notifications.asStateFlow()

    private val _promotions = MutableStateFlow(MockData.promotions)
    val promotions: StateFlow<List<Promotion>> = _promotions.asStateFlow()

    private val _markets = MutableStateFlow(MockData.markets)
    val markets: StateFlow<List<Market>> = _markets.asStateFlow()

    // ============ 统计数据 ============
    val todayOrders: Int get() = _orders.value.size
    val todayIncome: Double get() = _orders.value.sumOf { it.totalAmount }
    val activeMerchants: Int get() = _merchants.value.count { it.status == "ACTIVE" }
    val pendingOrders: Int get() = _orders.value.count { it.status == "PENDING" }
    val unreadNotificationCount: Int get() = _notifications.value.count { !it.isRead }

    // ============ 认证 ============
    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = RetrofitClient.apiService.login(
                    com.example.marketmanager.data.api.LoginRequest(email, password)
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        RetrofitClient.authToken = it.token
                        _isLoggedIn.value = true
                        _currentUser.value = User(
                            id = it.user.id,
                            name = it.user.name,
                            email = it.user.email,
                            phone = null,
                            role = it.user.role,
                            registrationDate = ""
                        )
                        // 登录成功后加载数据
                        loadAllData()
                        onSuccess()
                    } ?: run {
                        _errorMessage.value = "登录失败：响应数据为空"
                    }
                } else {
                    val errBody = response.errorBody()?.string()
                    _errorMessage.value = if (errBody?.contains("密码错误") == true) "密码错误"
                    else if (errBody?.contains("不存在") == true) "用户不存在"
                    else "登录失败：${response.code()}"
                }
            } catch (e: IOException) {
                // 仅网络不可达时使用本地Mock数据
                Log.w("MainViewModel", "网络不可用，使用本地Mock数据", e)
                _isLoggedIn.value = true
                _currentUser.value = MockData.users.firstOrNull()
                loadMockData()
                _errorMessage.value = null
                onSuccess()
            } catch (e: Exception) {
                // 其他异常（如Gson解析错误）显示给用户
                Log.e("MainViewModel", "登录异常", e)
                _errorMessage.value = "网络连接失败：${e.javaClass.simpleName}: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register(name: String, email: String, password: String, phone: String = "", onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = RetrofitClient.apiService.register(
                    com.example.marketmanager.data.api.RegisterRequest(name, email, password)
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        RetrofitClient.authToken = it.token
                        _isLoggedIn.value = true
                        _currentUser.value = User(
                            id = it.user.id, name = it.user.name,
                            email = it.user.email, phone = phone,
                            role = "CONSUMER", registrationDate = ""
                        )
                        loadAllData()
                        onSuccess()
                    } ?: run {
                        _errorMessage.value = "注册失败：响应数据为空"
                    }
                } else {
                    val errBody = response.errorBody()?.string()
                    _errorMessage.value = if (errBody?.contains("已注册") == true) "该邮箱已注册"
                    else "注册失败：${response.code()}"
                }
            } catch (e: IOException) {
                Log.w("MainViewModel", "网络不可用，使用本地Mock数据", e)
                _isLoggedIn.value = true
                _currentUser.value = MockData.users.firstOrNull()
                loadMockData()
                _errorMessage.value = null
                onSuccess()
            } catch (e: Exception) {
                Log.e("MainViewModel", "注册异常", e)
                _errorMessage.value = "网络连接失败：${e.javaClass.simpleName}: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        RetrofitClient.authToken = null
        _isLoggedIn.value = false
        _currentUser.value = null
        loadMockData()
    }

    // ============ 数据加载 ============
    private fun loadAllData() {
        viewModelScope.launch {
            try {
                val merchantsResp = RetrofitClient.apiService.getMerchants()
                if (merchantsResp.isSuccessful) merchantsResp.body()?.let { _merchants.value = it }

                val productsResp = RetrofitClient.apiService.getProducts()
                if (productsResp.isSuccessful) productsResp.body()?.let { _products.value = it }

                val ordersResp = RetrofitClient.apiService.getOrders()
                if (ordersResp.isSuccessful) ordersResp.body()?.let { _orders.value = it }

                val usersResp = RetrofitClient.apiService.getUsers()
                if (usersResp.isSuccessful) usersResp.body()?.let { _users.value = it }

                val groupBuysResp = RetrofitClient.apiService.getGroupBuys()
                if (groupBuysResp.isSuccessful) groupBuysResp.body()?.let { _groupBuys.value = it }

                val notificationsResp = RetrofitClient.apiService.getNotifications()
                if (notificationsResp.isSuccessful) notificationsResp.body()?.let { _notifications.value = it }

                val promotionsResp = RetrofitClient.apiService.getPromotions()
                if (promotionsResp.isSuccessful) promotionsResp.body()?.let { _promotions.value = it }

            } catch (e: Exception) {
                loadMockData()
            }
        }
    }

    private fun loadMockData() {
        _merchants.value = MockData.merchants
        _products.value = MockData.products
        _users.value = MockData.users
        _orders.value = MockData.orders
        _groupBuys.value = MockData.groupBuys
        _notifications.value = MockData.notifications
        _promotions.value = MockData.promotions
        _markets.value = MockData.markets
    }

    // ============ 商户管理 ============
    fun addMerchant(merchant: Merchant) {
        _merchants.value = _merchants.value + merchant
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.createMerchant(mapOf(
                    "name" to merchant.name, "owner" to merchant.owner,
                    "phone" to merchant.phone, "address" to merchant.address,
                    "stallNumber" to merchant.stallNumber, "category" to merchant.category
                ))
            } catch (_: Exception) { }
        }
    }

    fun updateMerchant(merchant: Merchant) {
        _merchants.value = _merchants.value.map { if (it.id == merchant.id) merchant else it }
    }

    fun deleteMerchant(merchantId: String) {
        _merchants.value = _merchants.value.filter { it.id != merchantId }
    }

    // ============ 商品管理 ============
    fun addProduct(product: Product) {
        _products.value = _products.value + product
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.createProduct(mapOf(
                    "name" to product.name, "merchantId" to product.merchantId,
                    "category" to product.category, "price" to product.price,
                    "unit" to product.unit, "stock" to product.stock
                ))
            } catch (_: Exception) { }
        }
    }

    fun updateProduct(product: Product) {
        _products.value = _products.value.map { if (it.id == product.id) product else it }
    }

    fun deleteProduct(productId: String) {
        _products.value = _products.value.filter { it.id != productId }
    }

    // ============ 用户管理 ============
    fun addUser(user: User) {
        _users.value = _users.value + user
    }

    fun updateUser(user: User) {
        _users.value = _users.value.map { if (it.id == user.id) user else it }
    }

    fun deleteUser(userId: String) {
        _users.value = _users.value.filter { it.id != userId }
    }

    // ============ 订单管理 ============
    fun addOrder(order: Order) {
        _orders.value = _orders.value + order
    }

    fun updateOrderStatus(orderId: String, status: String) {
        _orders.value = _orders.value.map { if (it.id == orderId) it.copy(status = status) else it }
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.updateOrderStatus(orderId,
                    com.example.marketmanager.data.api.StatusUpdateRequest(status))
            } catch (_: Exception) { }
        }
    }

    // ============ 团购管理 ============
    fun addGroupBuy(groupBuy: GroupBuy) {
        _groupBuys.value = _groupBuys.value + groupBuy
    }

    fun updateGroupBuy(groupBuy: GroupBuy) {
        _groupBuys.value = _groupBuys.value.map { if (it.id == groupBuy.id) groupBuy else it }
    }

    fun deleteGroupBuy(groupBuyId: String) {
        _groupBuys.value = _groupBuys.value.filter { it.id != groupBuyId }
    }

    // ============ 搜索 ============
    fun searchMerchants(query: String): List<Merchant> =
        _merchants.value.filter { it.name.contains(query, ignoreCase = true) || it.owner.contains(query, ignoreCase = true) }

    fun searchProducts(query: String): List<Product> =
        _products.value.filter { it.name.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true) }

    fun searchUsers(query: String): List<User> =
        _users.value.filter { it.name.contains(query, ignoreCase = true) || it.email.contains(query, ignoreCase = true) }

    // ============ 通知 ============
    fun markNotificationAsRead(notificationId: String) {
        _notifications.value = _notifications.value.map { if (it.id == notificationId) it.copy(isRead = true) else it }
        viewModelScope.launch {
            try { RetrofitClient.apiService.markNotificationRead(notificationId) } catch (_: Exception) { }
        }
    }

    fun markAllNotificationsAsRead() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
        viewModelScope.launch {
            try { RetrofitClient.apiService.markAllNotificationsRead() } catch (_: Exception) { }
        }
    }

    // ============ 促销 ============
    fun addPromotion(promotion: Promotion) {
        _promotions.value = _promotions.value + promotion
    }

    fun updatePromotion(promotion: Promotion) {
        _promotions.value = _promotions.value.map { if (it.id == promotion.id) promotion else it }
    }

    fun deletePromotion(promotionId: String) {
        _promotions.value = _promotions.value.filter { it.id != promotionId }
    }

    // ============ 市场 ============
    fun getMarketById(marketId: String): Market? = _markets.value.find { it.id == marketId }

    fun updateMarket(market: Market) {
        _markets.value = _markets.value.map { if (it.id == market.id) market else it }
    }
}

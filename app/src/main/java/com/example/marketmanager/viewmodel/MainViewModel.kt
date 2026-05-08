package com.example.marketmanager.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marketmanager.data.MockData
import com.example.marketmanager.data.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
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

    // 统计数据
    val todayOrders: Int get() = _orders.value.size
    val todayIncome: Double get() = _orders.value.sumOf { it.totalAmount }
    val activeMerchants: Int get() = _merchants.value.count { it.status == MerchantStatus.ACTIVE }
    val pendingOrders: Int get() = _orders.value.count { it.status == OrderStatus.PENDING }

    // 商户管理
    fun addMerchant(merchant: Merchant) {
        _merchants.value = _merchants.value + merchant
    }

    fun updateMerchant(merchant: Merchant) {
        _merchants.value = _merchants.value.map { 
            if (it.id == merchant.id) merchant else it 
        }
    }

    fun deleteMerchant(merchantId: String) {
        _merchants.value = _merchants.value.filter { it.id != merchantId }
    }

    // 商品管理
    fun addProduct(product: Product) {
        _products.value = _products.value + product
    }

    fun updateProduct(product: Product) {
        _products.value = _products.value.map { 
            if (it.id == product.id) product else it 
        }
    }

    fun deleteProduct(productId: String) {
        _products.value = _products.value.filter { it.id != productId }
    }

    // 用户管理
    fun addUser(user: User) {
        _users.value = _users.value + user
    }

    fun updateUser(user: User) {
        _users.value = _users.value.map { 
            if (it.id == user.id) user else it 
        }
    }

    fun deleteUser(userId: String) {
        _users.value = _users.value.filter { it.id != userId }
    }

    // 订单管理
    fun addOrder(order: Order) {
        _orders.value = _orders.value + order
    }

    fun updateOrderStatus(orderId: String, status: OrderStatus) {
        _orders.value = _orders.value.map { 
            if (it.id == orderId) it.copy(status = status) else it 
        }
    }

    // 团购管理
    fun addGroupBuy(groupBuy: GroupBuy) {
        _groupBuys.value = _groupBuys.value + groupBuy
    }

    fun updateGroupBuy(groupBuy: GroupBuy) {
        _groupBuys.value = _groupBuys.value.map { 
            if (it.id == groupBuy.id) groupBuy else it 
        }
    }

    fun deleteGroupBuy(groupBuyId: String) {
        _groupBuys.value = _groupBuys.value.filter { it.id != groupBuyId }
    }

    // 搜索功能
    fun searchMerchants(query: String): List<Merchant> {
        return _merchants.value.filter { 
            it.name.contains(query, ignoreCase = true) || 
            it.owner.contains(query, ignoreCase = true) ||
            it.category.contains(query, ignoreCase = true)
        }
    }

    fun searchProducts(query: String): List<Product> {
        return _products.value.filter { 
            it.name.contains(query, ignoreCase = true) || 
            it.category.contains(query, ignoreCase = true)
        }
    }

    fun searchUsers(query: String): List<User> {
        return _users.value.filter { 
            it.name.contains(query, ignoreCase = true) || 
            it.email.contains(query, ignoreCase = true)
        }
    }
}
package com.example.pharmacyapp.repository

import com.example.pharmacyapp.ui.history.Purchase
import com.example.pharmacyapp.ui.history.PurchaseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

class PurchaseRepository {
    private val _purchases = MutableStateFlow<List<Purchase>>(emptyList())
    val purchases: StateFlow<List<Purchase>> = _purchases

    fun addPurchase(product: com.example.pharmacyapp.model.Product) {
        val newPurchase = Purchase(
            id = System.currentTimeMillis().toString(),
            date = Date(),
            items = listOf(
                PurchaseItem(
                    name = product.name,
                    quantity = 1,
                    price = product.price
                )
            ),
            totalAmount = product.price
        )
        
        val currentList = _purchases.value.toMutableList()
        currentList.add(0, newPurchase) // Add to the beginning of the list
        _purchases.value = currentList
    }

    companion object {
        @Volatile
        private var instance: PurchaseRepository? = null

        fun getInstance(): PurchaseRepository {
            return instance ?: synchronized(this) {
                instance ?: PurchaseRepository().also { instance = it }
            }
        }
    }
} 
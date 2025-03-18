package com.example.pharmacyapp.repository

import android.content.Context
import com.example.pharmacyapp.data.UserPreferences
import com.example.pharmacyapp.data.model.Medicine
import com.example.pharmacyapp.ui.history.Purchase
import com.example.pharmacyapp.ui.history.PurchaseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

class PurchaseRepository(private val context: Context) {
    private val userPreferences = UserPreferences(context)
    private val _purchases = MutableStateFlow<List<Purchase>>(emptyList())
    val purchases: StateFlow<List<Purchase>> = _purchases

    init {
        // Загружаем сохраненные покупки при инициализации
        _purchases.value = userPreferences.getPurchases()
    }

    fun addPurchase(medicine: Medicine, quantity: Int = 1) {
        val newPurchase = Purchase(
            id = System.currentTimeMillis().toString(),
            date = Date(),
            items = listOf(
                PurchaseItem(
                    name = medicine.name,
                    quantity = quantity,
                    price = medicine.price
                )
            ),
            totalAmount = medicine.price * quantity
        )
        
        val currentList = _purchases.value.toMutableList()
        currentList.add(0, newPurchase) // Добавляем в начало списка
        _purchases.value = currentList
        
        // Сохраняем обновленный список покупок
        userPreferences.savePurchases(currentList)
    }

    companion object {
        @Volatile
        private var instance: PurchaseRepository? = null

        fun getInstance(context: Context): PurchaseRepository {
            return instance ?: synchronized(this) {
                instance ?: PurchaseRepository(context.applicationContext).also { instance = it }
            }
        }
    }
} 
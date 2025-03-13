package com.example.pharmacyapp.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class Cart {
    private val _items = MutableStateFlow<Map<Product, Int>>(emptyMap())
    val items: StateFlow<Map<Product, Int>> = _items.asStateFlow()

    fun addItem(product: Product) {
        val currentItems = _items.value.toMutableMap()
        currentItems[product] = (currentItems[product] ?: 0) + 1
        _items.value = currentItems
    }

    fun removeItem(product: Product) {
        val currentItems = _items.value.toMutableMap()
        val currentQuantity = currentItems[product] ?: 0
        if (currentQuantity > 1) {
            currentItems[product] = currentQuantity - 1
        } else {
            currentItems.remove(product)
        }
        _items.value = currentItems
    }

    fun clearCart() {
        _items.value = emptyMap()
    }

    fun getTotalPrice(): Double {
        return _items.value.entries.sumOf { (product, quantity) ->
            product.price * quantity
        }
    }

    fun getItemCount(): Int {
        return _items.value.values.sum()
    }
} 
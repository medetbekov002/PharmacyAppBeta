package com.example.pharmacyapp.ui.history

import java.util.Date

data class Purchase(
    val id: String,
    val date: Date,
    val items: List<PurchaseItem>,
    val totalAmount: Double
)

data class PurchaseItem(
    val name: String,
    val quantity: Int,
    val price: Double
) 
package com.example.pharmacyapp.ui.discounts

import java.util.Date

data class Discount(
    val id: String,
    val title: String,
    val description: String,
    val percentOff: Int,
    val validUntil: Date,
    val promoCode: String,
    val isActive: Boolean
)

data class PromoCodeResult(
    val isValid: Boolean,
    val discount: Discount?,
    val errorMessage: String?
) 
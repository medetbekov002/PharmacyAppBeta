package com.example.pharmacyapp.ui.payment

import java.util.Date

data class PaymentMethod(
    val id: String,
    val type: PaymentType,
    val details: String,
    val lastUsed: Date?
)

enum class PaymentType {
    CREDIT_CARD,
    QR_CODE
}

data class Transaction(
    val id: String,
    val amount: Double,
    val date: Date,
    val status: TransactionStatus,
    val paymentMethod: PaymentMethod
)

enum class TransactionStatus {
    SUCCESS,
    FAILED,
    PENDING
} 
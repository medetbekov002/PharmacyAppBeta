package com.example.pharmacyapp.data.model

data class Discount(
    val id: String = "",
    val title: String,
    val description: String,
    val percentage: Int,
    val validUntil: Long,
    val medicineIds: List<String> = emptyList()
) 
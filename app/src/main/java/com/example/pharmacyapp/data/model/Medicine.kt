package com.example.pharmacyapp.data.model

data class Medicine(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val quantity: Int,
    val imageUrl: String? = null
) 
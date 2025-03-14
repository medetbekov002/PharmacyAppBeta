package com.example.pharmacyapp.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val isPopular: Boolean = false
) 
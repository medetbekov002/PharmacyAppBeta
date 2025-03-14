package com.example.pharmacyapp.ui.home

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val categoryId: String,
    val isPopular: Boolean = false,
    val inStock: Boolean = true
)

data class Category(
    val id: String,
    val name: String,
    val iconUrl: String
) 
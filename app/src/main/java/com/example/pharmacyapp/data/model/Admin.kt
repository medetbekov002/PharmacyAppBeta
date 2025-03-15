package com.example.pharmacyapp.data.model

data class Admin(
    val email: String,
    val password: String,
    val isAdmin: Boolean = true
) 
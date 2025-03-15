package com.example.pharmacyapp.data.model

data class User(
    val email: String,
    val password: String,
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var birthYear: Int = 0
) 
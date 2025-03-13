package com.example.pharmacyapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pharmacies")
data class Pharmacy(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val address: String,
    val phone: String,
    val workingHours: String,
    val latitude: Double,
    val longitude: Double,
    val isOpen: Boolean = true
) 
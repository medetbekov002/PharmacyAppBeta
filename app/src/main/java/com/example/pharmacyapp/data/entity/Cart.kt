package com.example.pharmacyapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.Date

@Entity(tableName = "carts")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val pharmacyId: Long,
    val createdAt: Date = Date()
)

@Entity(
    tableName = "cart_items",
    foreignKeys = [
        ForeignKey(
            entity = Cart::class,
            parentColumns = ["id"],
            childColumns = ["cartId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("cartId"),
        Index("productId")
    ]
)
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cartId: Long,
    val productId: Long,
    val quantity: Int
) 
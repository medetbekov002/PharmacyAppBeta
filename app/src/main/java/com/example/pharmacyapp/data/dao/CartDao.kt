package com.example.pharmacyapp.data.dao

import androidx.room.*
import com.example.pharmacyapp.data.entity.Cart
import com.example.pharmacyapp.data.entity.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM carts WHERE userId = :userId AND pharmacyId = :pharmacyId")
    suspend fun getCart(userId: Long, pharmacyId: Long): Cart?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: Cart): Long

    @Update
    suspend fun updateCart(cart: Cart)

    @Delete
    suspend fun deleteCart(cart: Cart)
}

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cart_items WHERE cartId = :cartId")
    fun getCartItems(cartId: Long): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Query("DELETE FROM cart_items WHERE cartId = :cartId")
    suspend fun deleteAllCartItems(cartId: Long)
} 
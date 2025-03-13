package com.example.pharmacyapp.data.dao

import androidx.room.*
import com.example.pharmacyapp.data.entity.Pharmacy
import kotlinx.coroutines.flow.Flow

@Dao
interface PharmacyDao {
    @Query("SELECT * FROM pharmacies")
    fun getAllPharmacies(): Flow<List<Pharmacy>>

    @Query("SELECT * FROM pharmacies WHERE id = :pharmacyId")
    suspend fun getPharmacyById(pharmacyId: Long): Pharmacy?

    @Query("SELECT * FROM pharmacies WHERE isOpen = 1")
    fun getOpenPharmacies(): Flow<List<Pharmacy>>

    @Query("SELECT * FROM pharmacies WHERE name LIKE '%' || :query || '%' OR address LIKE '%' || :query || '%'")
    fun searchPharmacies(query: String): Flow<List<Pharmacy>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPharmacy(pharmacy: Pharmacy)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPharmacies(pharmacies: List<Pharmacy>)

    @Update
    suspend fun updatePharmacy(pharmacy: Pharmacy)

    @Delete
    suspend fun deletePharmacy(pharmacy: Pharmacy)
} 
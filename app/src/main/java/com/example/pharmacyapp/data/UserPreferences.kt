package com.example.pharmacyapp.data

import android.content.Context
import android.content.SharedPreferences
import com.example.pharmacyapp.data.model.Admin
import com.example.pharmacyapp.data.model.User
import com.example.pharmacyapp.data.model.Medicine
import com.example.pharmacyapp.data.model.Discount
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    init {
        // Принудительно обновляем данные админа
        val defaultAdmin = Admin(
            email = "TheBezNazvaniya@gmail.com",
            password = "1921681123"
        )
        saveAdmin(defaultAdmin)
        
        // Инициализируем список пользователей, если его еще нет
        if (!prefs.contains(KEY_ALL_USERS)) {
            prefs.edit().putString(KEY_ALL_USERS, gson.toJson(emptyList<User>())).apply()
        }
        // Инициализируем список лекарств, если его еще нет
        if (!prefs.contains(KEY_MEDICINES)) {
            prefs.edit().putString(KEY_MEDICINES, gson.toJson(emptyList<Medicine>())).apply()
        }
        // Инициализируем список скидок, если его еще нет
        if (!prefs.contains(KEY_DISCOUNTS)) {
            prefs.edit().putString(KEY_DISCOUNTS, gson.toJson(emptyList<Discount>())).apply()
        }
    }

    fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        prefs.edit()
            .putString(KEY_USER, userJson)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .putBoolean(KEY_IS_ADMIN, false)
            .apply()
        
        // Добавляем пользователя в общий список, если его там еще нет
        addUserToList(user)
    }

    fun saveAdmin(admin: Admin) {
        prefs.edit()
            .putString(KEY_ADMIN, gson.toJson(admin))
            .apply()
    }

    fun getUser(): User? {
        val userJson = prefs.getString(KEY_USER, null)
        return if (userJson != null) {
            try {
                gson.fromJson(userJson, User::class.java)
            } catch (e: Exception) {
                null
            }
        } else null
    }

    fun getAdmin(): Admin? {
        val adminJson = prefs.getString(KEY_ADMIN, null)
        return if (adminJson != null) {
            try {
                gson.fromJson(adminJson, Admin::class.java)
            } catch (e: Exception) {
                null
            }
        } else null
    }

    fun getAllUsers(): List<User> {
        val usersJson = prefs.getString(KEY_ALL_USERS, null)
        return if (usersJson != null) {
            try {
                val type = object : TypeToken<List<User>>() {}.type
                gson.fromJson<List<User>>(usersJson, type) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    fun addUserToList(user: User) {
        val currentUsers = getAllUsers().toMutableList()
        // Проверяем, нет ли уже пользователя с таким email
        val existingUserIndex = currentUsers.indexOfFirst { it.email == user.email }
        if (existingUserIndex != -1) {
            // Если пользователь существует, обновляем его данные
            currentUsers[existingUserIndex] = user
        } else {
            // Если пользователя нет, добавляем его
            currentUsers.add(user)
        }
        prefs.edit()
            .putString(KEY_ALL_USERS, gson.toJson(currentUsers))
            .apply()
    }

    fun updateUserProfile(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        birthYear: Int
    ) {
        val user = getUser()?.copy(
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            birthYear = birthYear
        )
        user?.let { 
            saveUser(it)
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun isAdmin(): Boolean {
        return prefs.getBoolean(KEY_IS_ADMIN, false)
    }

    fun loginAsAdmin() {
        prefs.edit()
            .remove(KEY_USER) // Удаляем данные обычного пользователя при входе как админ
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .putBoolean(KEY_IS_ADMIN, true)
            .apply()
    }

    fun logout() {
        prefs.edit()
            .remove(KEY_USER)
            .remove(KEY_IS_LOGGED_IN)
            .remove(KEY_IS_ADMIN)
            .apply()
    }

    // Методы для работы с лекарствами
    fun saveMedicine(medicine: Medicine) {
        val medicines = getMedicines().toMutableList()
        medicines.add(medicine)
        prefs.edit()
            .putString(KEY_MEDICINES, gson.toJson(medicines))
            .apply()
    }

    fun getMedicines(): List<Medicine> {
        val json = prefs.getString(KEY_MEDICINES, null)
        return if (json != null) {
            try {
                val type = object : TypeToken<List<Medicine>>() {}.type
                gson.fromJson(json, type) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    fun updateMedicine(medicine: Medicine) {
        val medicines = getMedicines().toMutableList()
        val index = medicines.indexOfFirst { it.id == medicine.id }
        if (index != -1) {
            medicines[index] = medicine
            prefs.edit()
                .putString(KEY_MEDICINES, gson.toJson(medicines))
                .apply()
        }
    }

    fun deleteMedicine(medicineId: String) {
        val medicines = getMedicines().toMutableList()
        medicines.removeAll { it.id == medicineId }
        prefs.edit()
            .putString(KEY_MEDICINES, gson.toJson(medicines))
            .apply()
    }

    // Методы для работы со скидками
    fun saveDiscount(discount: Discount) {
        val discounts = getDiscounts().toMutableList()
        discounts.add(discount)
        prefs.edit()
            .putString(KEY_DISCOUNTS, gson.toJson(discounts))
            .apply()
    }

    fun getDiscounts(): List<Discount> {
        val json = prefs.getString(KEY_DISCOUNTS, null)
        return if (json != null) {
            try {
                val type = object : TypeToken<List<Discount>>() {}.type
                gson.fromJson(json, type) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    fun updateDiscount(discount: Discount) {
        val discounts = getDiscounts().toMutableList()
        val index = discounts.indexOfFirst { it.id == discount.id }
        if (index != -1) {
            discounts[index] = discount
            prefs.edit()
                .putString(KEY_DISCOUNTS, gson.toJson(discounts))
                .apply()
        }
    }

    fun deleteDiscount(discountId: String) {
        val discounts = getDiscounts().toMutableList()
        discounts.removeAll { it.id == discountId }
        prefs.edit()
            .putString(KEY_DISCOUNTS, gson.toJson(discounts))
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "pharmacy_app_prefs"
        private const val KEY_USER = "user"
        private const val KEY_ADMIN = "admin"
        private const val KEY_ALL_USERS = "all_users"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_IS_ADMIN = "is_admin"
        private const val KEY_MEDICINES = "medicines"
        private const val KEY_DISCOUNTS = "discounts"
    }
} 
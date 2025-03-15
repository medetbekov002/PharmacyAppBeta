package com.example.pharmacyapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.pharmacyapp.data.UserPreferences
import com.example.pharmacyapp.databinding.ActivityMainBinding
import com.example.pharmacyapp.utils.LocaleManager

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var userPreferences: UserPreferences

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userPreferences = UserPreferences(this)

        // Инициализация NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Настраиваем нижнюю навигацию
        binding.bottomNavigation?.setupWithNavController(navController)

        // Скрываем/показываем нижнюю навигацию в зависимости от экрана
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.registerFragment, R.id.adminPanelFragment -> {
                    binding.bottomNavigation?.visibility = View.GONE
                }
                else -> {
                    if (!userPreferences.isAdmin()) {
                        binding.bottomNavigation?.visibility = View.VISIBLE
                    }
                }
            }
        }

        // Обработка нажатий в нижней навигации
        binding.bottomNavigation?.setOnItemSelectedListener { menuItem ->
            if (userPreferences.isAdmin()) {
                navController.navigate(R.id.action_global_adminPanel)
                false
            } else {
                navController.navigate(menuItem.itemId)
                true
            }
        }

        // Set initial theme
        val sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false)
        setDarkTheme(isDarkTheme)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun setDarkTheme(isDark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun setNewLocale(language: String) {
        LocaleManager.setNewLocale(this, language)
        restartApp()
    }

    private fun restartApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
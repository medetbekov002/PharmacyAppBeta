package com.example.pharmacyapp

import android.app.Application
import android.content.Context
import com.example.pharmacyapp.utils.LocaleManager

class PharmacyApp : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    override fun onCreate() {
        super.onCreate()
        LocaleManager.applyLocale(this)
    }

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.applyLocale(this)
    }
} 
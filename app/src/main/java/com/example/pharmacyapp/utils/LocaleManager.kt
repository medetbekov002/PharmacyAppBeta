package com.example.pharmacyapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

object LocaleManager {
    private const val LANGUAGE_PREFS = "LanguagePrefs"
    private const val SELECTED_LANGUAGE = "SelectedLanguage"

    fun setLocale(context: Context): Context {
        return updateResources(context, getLanguage(context))
    }

    fun setNewLocale(context: Context, language: String) {
        persistLanguage(context, language)
        updateResources(context, language)
    }

    fun getLanguage(context: Context): String {
        val prefs = getPreferences(context)
        return prefs.getString(SELECTED_LANGUAGE, "en") ?: "en"
    }

    private fun persistLanguage(context: Context, language: String) {
        val prefs = getPreferences(context)
        prefs.edit().putString(SELECTED_LANGUAGE, language).apply()
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(configuration)
        } else {
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
            context
        }
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(LANGUAGE_PREFS, Context.MODE_PRIVATE)
    }

    fun applyLocale(context: Context) {
        val locale = Locale(getLanguage(context))
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
} 
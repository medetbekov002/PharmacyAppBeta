package com.example.pharmacyapp.ui.settings

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pharmacyapp.MainActivity
import com.example.pharmacyapp.R
import com.example.pharmacyapp.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(requireActivity().getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLanguageButton()
        setupThemeSwitch()
        setupButtons()
    }

    private fun setupLanguageButton() {
        val currentLocale = getCurrentLocale()
        binding.languageButton.text = if (currentLocale == Locale("ru")) {
            getString(R.string.language_russian)
        } else {
            getString(R.string.language_english)
        }

        binding.languageButton.setOnClickListener {
            val newLocale = if (currentLocale == Locale("ru")) {
                Locale("en")
            } else {
                Locale("ru")
            }
            setLocale(newLocale)
        }
    }

    private fun setupThemeSwitch() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isDarkTheme.collectLatest { isDark ->
                binding.themeSwitch.isChecked = isDark
            }
        }

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkTheme(isChecked)
            (activity as? MainActivity)?.setDarkTheme(isChecked)
        }
    }

    private fun setupButtons() {
        binding.aboutUsButton.setOnClickListener {
            showAboutUsDialog()
        }

        binding.contactUsButton.setOnClickListener {
            showContactDialog()
        }
    }

    private fun showAboutUsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.settings_about)
            .setMessage(getString(R.string.about_us_message))
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showContactDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.settings_contact)
            .setMessage(getString(R.string.contact_message))
            .setPositiveButton("OK", null)
            .show()
    }

    private fun getCurrentLocale(): Locale {
        return resources.configuration.locales[0]
    }

    private fun setLocale(locale: Locale) {
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Recreate activity to apply changes
        activity?.recreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 
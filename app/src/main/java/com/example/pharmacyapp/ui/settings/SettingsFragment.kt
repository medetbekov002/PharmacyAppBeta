package com.example.pharmacyapp.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pharmacyapp.MainActivity
import com.example.pharmacyapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

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

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("ThemePrefs", 0)
        
        // Set initial switch state
        binding.themeSwitch.isChecked = sharedPreferences.getBoolean("isDarkTheme", false)

        // Set up theme switch
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            (activity as? MainActivity)?.setDarkTheme(isChecked)
        }

        setupInfoButtons()
    }

    private fun setupInfoButtons() {
        binding.aboutUsButton.setOnClickListener {
            showAboutUsDialog()
        }

        binding.contactUsButton.setOnClickListener {
            showContactUsDialog()
        }
    }

    private fun showAboutUsDialog() {
        val message = """
            Pharmacy App is your trusted companion for managing your health and wellness needs.
            
            We provide:
            • Easy access to medicines
            • Special discounts and offers
            • Secure payment options
            • Purchase history tracking
            
            Version: 1.0.0
        """.trimIndent()

        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("About Us")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showContactUsDialog() {
        val message = """
            Get in touch with us:
            
            Email: support@pharmacyapp.com
            Phone: +1 (555) 123-4567
            Address: 123 Health Street, Medical City
            
            We're here to help!
        """.trimIndent()

        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Contact Us")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 
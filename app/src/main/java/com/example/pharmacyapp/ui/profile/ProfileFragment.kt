package com.example.pharmacyapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pharmacyapp.R
import com.example.pharmacyapp.data.UserPreferences
import com.example.pharmacyapp.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        userPreferences = UserPreferences(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Загружаем данные пользователя
        loadUserData()

        binding.saveButton.setOnClickListener {
            saveUserData()
        }

        binding.homeButton.setOnClickListener {
            if (userPreferences.isAdmin()) {
                findNavController().navigate(R.id.action_global_adminPanel)
            } else {
                findNavController().navigate(R.id.action_global_home)
            }
        }
    }

    private fun loadUserData() {
        val user = userPreferences.getUser()
        user?.let {
            binding.firstNameEditText.setText(it.firstName)
            binding.lastNameEditText.setText(it.lastName)
            binding.phoneEditText.setText(it.phoneNumber)
            if (it.birthYear > 0) {
                binding.birthYearEditText.setText(it.birthYear.toString())
            }
        }
    }

    private fun saveUserData() {
        val firstName = binding.firstNameEditText.text.toString()
        val lastName = binding.lastNameEditText.text.toString()
        val phone = binding.phoneEditText.text.toString()
        val birthYearStr = binding.birthYearEditText.text.toString()
        val birthYear = if (birthYearStr.isNotEmpty()) birthYearStr.toInt() else 0

        if (validateInput(firstName, lastName, phone, birthYear)) {
            userPreferences.updateUserProfile(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phone,
                birthYear = birthYear
            )
            showMessage(getString(R.string.profile_saved))
        }
    }

    private fun validateInput(
        firstName: String,
        lastName: String,
        phone: String,
        birthYear: Int
    ): Boolean {
        if (firstName.isEmpty()) {
            showError("Введите имя")
            return false
        }
        if (lastName.isEmpty()) {
            showError("Введите фамилию")
            return false
        }
        if (phone.isEmpty()) {
            showError("Введите номер телефона")
            return false
        }
        if (birthYear <= 1900 || birthYear > 2024) {
            showError("Введите корректный год рождения")
            return false
        }
        return true
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 
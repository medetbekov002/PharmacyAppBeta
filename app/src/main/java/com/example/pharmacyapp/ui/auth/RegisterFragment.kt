package com.example.pharmacyapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pharmacyapp.R
import com.example.pharmacyapp.data.UserPreferences
import com.example.pharmacyapp.data.model.User
import com.example.pharmacyapp.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        userPreferences = UserPreferences(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginTextView.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (validateInput(email, password, confirmPassword)) {
                try {
                    // Проверяем, не существует ли уже пользователь с таким email
                    val existingUser = userPreferences.getAllUsers().find { it.email == email }
                    if (existingUser != null) {
                        showError("Пользователь с таким email уже существует")
                        return@setOnClickListener
                    }

                    // Создаем нового пользователя
                    val user = User(
                        email = email,
                        password = password,
                        firstName = "",
                        lastName = "",
                        phoneNumber = "",
                        birthYear = 0
                    )

                    // Сохраняем пользователя
                    userPreferences.saveUser(user)

                    // Показываем сообщение об успешной регистрации
                    Snackbar.make(binding.root, "Регистрация успешна!", Snackbar.LENGTH_SHORT).show()

                    // Переходим на главный экран
                    findNavController().navigate(R.id.action_register_to_home)
                } catch (e: Exception) {
                    showError("Ошибка при регистрации: ${e.message}")
                }
            }
        }
    }

    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
        if (email.isEmpty()) {
            showError("Email не может быть пустым")
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Введите корректный email")
            return false
        }
        if (password.isEmpty()) {
            showError("Пароль не может быть пустым")
            return false
        }
        if (password.length < 6) {
            showError("Пароль должен содержать минимум 6 символов")
            return false
        }
        if (password != confirmPassword) {
            showError("Пароли не совпадают")
            return false
        }
        return true
    }

    private fun showError(message: String) {
        if (view != null) {
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 
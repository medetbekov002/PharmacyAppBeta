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
import com.example.pharmacyapp.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        userPreferences = UserPreferences(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Проверяем, залогинен ли пользователь
        if (userPreferences.isLoggedIn()) {
            // Если залогинен как админ, переходим в админ панель
            if (userPreferences.isAdmin()) {
                findNavController().navigate(R.id.action_login_to_adminPanel)
            } else {
                // Иначе переходим на главный экран
                findNavController().navigate(R.id.action_login_to_home)
            }
            return
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (validateInput(email, password)) {
                // Проверяем, является ли это админом
                val admin = userPreferences.getAdmin()
                if (admin != null && email == admin.email && password == admin.password) {
                    userPreferences.loginAsAdmin()
                    findNavController().navigate(R.id.action_login_to_adminPanel)
                    return@setOnClickListener
                }

                // Если не админ, проверяем обычного пользователя
                val users = userPreferences.getAllUsers()
                val user = users.find { it.email == email }

                if (user != null && user.password == password) {
                    userPreferences.saveUser(user)
                    findNavController().navigate(R.id.action_login_to_home)
                } else {
                    showError("Неверный email или пароль")
                }
            }
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isBlank()) {
            showError("Введите email")
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Введите корректный email")
            return false
        }
        if (password.isBlank()) {
            showError("Введите пароль")
            return false
        }
        return true
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 
package com.example.pharmacyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pharmacyapp.R
import com.example.pharmacyapp.data.UserPreferences
import com.example.pharmacyapp.data.model.Medicine
import com.example.pharmacyapp.databinding.FragmentHomeBinding
import com.example.pharmacyapp.ui.adapters.ProductsAdapter
import com.example.pharmacyapp.ui.auth.LoginFragment
import com.example.pharmacyapp.ui.discounts.DiscountsFragment
import com.example.pharmacyapp.ui.history.HistoryFragment
import com.example.pharmacyapp.ui.payment.PaymentFragment
import com.example.pharmacyapp.ui.profile.ProfileFragment
import com.example.pharmacyapp.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var userPreferences: UserPreferences
    private var allProducts = mutableListOf<Medicine>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        userPreferences = UserPreferences(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupRecyclerView()
        setupSearch()
        loadMedicines()
    }

    private fun setupUI() {
        with(binding) {
            homeTitle.text = getString(R.string.home_title)
            categoriesTitle.text = getString(R.string.categories_title)
            popularProductsTitle.text = getString(R.string.popular_products)
        }
    }

    private fun setupRecyclerView() {
        productsAdapter = ProductsAdapter { medicine -> handleProductClick(medicine) }
        
        binding.productsRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = productsAdapter
        }
    }

    private fun setupSearch() {
        val searchEditText = binding.root.findViewById<TextInputEditText>(R.id.searchEditText)
        searchEditText?.addTextChangedListener { text ->
            val query = text?.toString()?.lowercase() ?: ""
            if (query.isEmpty()) {
                productsAdapter.submitList(allProducts)
            } else {
                val filteredProducts = allProducts.filter { medicine ->
                    medicine.name.lowercase().contains(query) ||
                    medicine.description.lowercase().contains(query) ||
                    medicine.category.lowercase().contains(query)
                }
                productsAdapter.submitList(filteredProducts)
            }
        }
    }

    private fun loadMedicines() {
        allProducts = userPreferences.getMedicines().toMutableList()
        productsAdapter.submitList(allProducts)
    }

    private fun handleProductClick(medicine: Medicine) {
        // Здесь можно добавить логику добавления в корзину
        val message = getString(R.string.add_to_cart_message, medicine.name)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        // Обновляем список лекарств при возвращении на экран
        loadMedicines()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 
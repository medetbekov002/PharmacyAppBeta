package com.example.pharmacyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pharmacyapp.R
import com.example.pharmacyapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupRecyclerView()
        loadSampleData()
    }

    private fun setupUI() {
        with(binding) {
            homeTitle.text = getString(R.string.home_title)
            searchInput.hint = getString(R.string.search_hint)
            categoriesTitle.text = getString(R.string.categories_title)
            popularProductsTitle.text = getString(R.string.popular_products)
        }
    }

    private fun setupRecyclerView() {
        productsAdapter = ProductsAdapter(emptyList()) { product ->
            handleProductClick(product)
        }
        
        binding.productsRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = productsAdapter
        }
    }

    private fun loadSampleData() {
        val sampleProducts = listOf(
            Product(
                id = "1",
                name = getString(R.string.product_1_name),
                description = getString(R.string.product_1_desc),
                price = 299.99,
                imageUrl = "url_to_image",
                categoryId = "painkillers",
                isPopular = true
            ),
            Product(
                id = "2",
                name = getString(R.string.product_2_name),
                description = getString(R.string.product_2_desc),
                price = 199.99,
                imageUrl = "url_to_image",
                categoryId = "painkillers"
            ),
            Product(
                id = "3",
                name = getString(R.string.product_3_name),
                description = getString(R.string.product_3_desc),
                price = 249.99,
                imageUrl = "url_to_image",
                categoryId = "painkillers"
            ),
            Product(
                id = "4",
                name = getString(R.string.product_4_name),
                description = getString(R.string.product_4_desc),
                price = 599.99,
                imageUrl = "url_to_image",
                categoryId = "antibiotics",
                isPopular = true
            ),
            Product(
                id = "5",
                name = getString(R.string.product_5_name),
                description = getString(R.string.product_5_desc),
                price = 399.99,
                imageUrl = "url_to_image",
                categoryId = "vitamins"
            ),
            Product(
                id = "6",
                name = getString(R.string.product_6_name),
                description = getString(R.string.product_6_desc),
                price = 449.99,
                imageUrl = "url_to_image",
                categoryId = "vitamins"
            ),
            Product(
                id = "7",
                name = getString(R.string.product_7_name),
                description = getString(R.string.product_7_desc),
                price = 899.99,
                imageUrl = "url_to_image",
                categoryId = "vitamins",
                isPopular = true
            ),
            Product(
                id = "8",
                name = getString(R.string.product_8_name),
                description = getString(R.string.product_8_desc),
                price = 499.99,
                imageUrl = "url_to_image",
                categoryId = "cold"
            ),
            Product(
                id = "9",
                name = getString(R.string.product_9_name),
                description = getString(R.string.product_9_desc),
                price = 349.99,
                imageUrl = "url_to_image",
                categoryId = "cold"
            ),
            Product(
                id = "10",
                name = getString(R.string.product_10_name),
                description = getString(R.string.product_10_desc),
                price = 199.99,
                imageUrl = "url_to_image",
                categoryId = "cold"
            ),
            Product(
                id = "11",
                name = getString(R.string.product_11_name),
                description = getString(R.string.product_11_desc),
                price = 399.99,
                imageUrl = "url_to_image",
                categoryId = "allergy"
            ),
            Product(
                id = "12",
                name = getString(R.string.product_12_name),
                description = getString(R.string.product_12_desc),
                price = 449.99,
                imageUrl = "url_to_image",
                categoryId = "allergy"
            ),
            Product(
                id = "13",
                name = getString(R.string.product_13_name),
                description = getString(R.string.product_13_desc),
                price = 1499.99,
                imageUrl = "url_to_image",
                categoryId = "firstaid",
                isPopular = true
            ),
            Product(
                id = "14",
                name = getString(R.string.product_14_name),
                description = getString(R.string.product_14_desc),
                price = 299.99,
                imageUrl = "url_to_image",
                categoryId = "firstaid"
            ),
            Product(
                id = "15",
                name = getString(R.string.product_15_name),
                description = getString(R.string.product_15_desc),
                price = 249.99,
                imageUrl = "url_to_image",
                categoryId = "firstaid"
            ),
            Product(
                id = "16",
                name = getString(R.string.product_16_name),
                description = getString(R.string.product_16_desc),
                price = 699.99,
                imageUrl = "url_to_image",
                categoryId = "vitamins"
            ),
            Product(
                id = "17",
                name = getString(R.string.product_17_name),
                description = getString(R.string.product_17_desc),
                price = 449.99,
                imageUrl = "url_to_image",
                categoryId = "vitamins"
            ),
            Product(
                id = "18",
                name = getString(R.string.product_18_name),
                description = getString(R.string.product_18_desc),
                price = 399.99,
                imageUrl = "url_to_image",
                categoryId = "vitamins"
            ),
            Product(
                id = "19",
                name = getString(R.string.product_19_name),
                description = getString(R.string.product_19_desc),
                price = 799.99,
                imageUrl = "url_to_image",
                categoryId = "vitamins",
                isPopular = true
            ),
            Product(
                id = "20",
                name = getString(R.string.product_20_name),
                description = getString(R.string.product_20_desc),
                price = 599.99,
                imageUrl = "url_to_image",
                categoryId = "vitamins"
            )
        )

        productsAdapter.updateProducts(sampleProducts)
    }

    private fun handleProductClick(product: Product) {
        val message = getString(R.string.add_to_cart)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 
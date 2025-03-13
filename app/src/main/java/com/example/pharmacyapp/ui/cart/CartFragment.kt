package com.example.pharmacyapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacyapp.adapter.CartAdapter
import com.example.pharmacyapp.databinding.FragmentCartBinding
import com.example.pharmacyapp.model.Cart
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cart: Cart by activityViewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupButtons()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onIncrement = { product -> cart.addItem(product) },
            onDecrement = { product -> cart.removeItem(product) }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            cart.items.collectLatest { items ->
                cartAdapter.submitList(items.entries.map { it.key })
                updateTotalPrice(items)
                updateEmptyState(items.isEmpty())
            }
        }
    }

    private fun setupButtons() {
        binding.checkoutButton.setOnClickListener {
            // Implement checkout logic
        }

        binding.clearCartButton.setOnClickListener {
            cart.clearCart()
        }
    }

    private fun updateTotalPrice(items: Map<*, Int>) {
        binding.totalPrice.text = "$ ${cart.getTotalPrice()}"
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.checkoutButton.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.clearCartButton.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 
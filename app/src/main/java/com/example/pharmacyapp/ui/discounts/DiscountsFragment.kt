package com.example.pharmacyapp.ui.discounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacyapp.R
import com.example.pharmacyapp.databinding.FragmentDiscountsBinding
import java.util.Date

class DiscountsFragment : Fragment() {
    private var _binding: FragmentDiscountsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var discountsAdapter: DiscountsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscountsBinding.inflate(inflater, container, false)
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
            // Set headers
            discountsTitle.text = getString(R.string.discounts_title)
            discountsSubtitle.text = getString(R.string.discounts_subtitle)
            
            // Set promo code section
            promoCodeTitle.text = getString(R.string.promo_code)
            promoCodeInput.hint = getString(R.string.enter_promo)
            applyPromoButton.text = getString(R.string.apply_promo)
            applyPromoButton.setOnClickListener { handlePromoCode() }
            
            // Set active discounts section
            activeDiscountsTitle.text = getString(R.string.active_discounts)
            noDiscountsMessage.text = getString(R.string.no_discounts)
        }
    }

    private fun setupRecyclerView() {
        discountsAdapter = DiscountsAdapter(emptyList()) { discount: Discount ->
            handleDiscountApply(discount)
        }
        
        binding.discountsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = discountsAdapter
        }
    }

    private fun loadSampleData() {
        val sampleDiscounts = listOf(
            Discount(
                id = "DISC1",
                title = getString(R.string.sample_discount_title_1),
                description = getString(R.string.sample_discount_desc_1),
                percentOff = 15,
                validUntil = Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000), // Week from now
                promoCode = "HEALTH15",
                isActive = true
            ),
            Discount(
                id = "DISC2",
                title = getString(R.string.sample_discount_title_2),
                description = getString(R.string.sample_discount_desc_2),
                percentOff = 25,
                validUntil = Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000), // 3 days from now
                promoCode = "WELLNESS25",
                isActive = true
            )
        )

        discountsAdapter.updateDiscounts(sampleDiscounts)
        updateDiscountsVisibility(sampleDiscounts)
    }

    private fun handlePromoCode() {
        val promoCode = binding.promoCodeInput.text?.toString()?.trim()
        if (promoCode.isNullOrEmpty()) {
            Toast.makeText(context, getString(R.string.enter_promo), Toast.LENGTH_SHORT).show()
            return
        }

        // Here you would typically validate the promo code with your backend
        Toast.makeText(context, getString(R.string.invalid_promo), Toast.LENGTH_SHORT).show()
    }

    private fun handleDiscountApply(discount: Discount) {
        // Here you would typically apply the discount to the user's cart or account
        val message = getString(R.string.discount_applied, discount.percentOff)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateDiscountsVisibility(discounts: List<Discount>) {
        with(binding) {
            if (discounts.isEmpty()) {
                discountsRecyclerView.visibility = View.GONE
                noDiscountsMessage.visibility = View.VISIBLE
            } else {
                discountsRecyclerView.visibility = View.VISIBLE
                noDiscountsMessage.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 
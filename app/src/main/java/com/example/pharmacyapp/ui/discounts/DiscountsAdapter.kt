package com.example.pharmacyapp.ui.discounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacyapp.R
import com.example.pharmacyapp.databinding.ItemDiscountBinding
import java.text.SimpleDateFormat
import java.util.Locale

class DiscountsAdapter(
    private var discounts: List<Discount>,
    private val onApplyDiscount: (Discount) -> Unit
) : RecyclerView.Adapter<DiscountsAdapter.DiscountViewHolder>() {

    inner class DiscountViewHolder(
        private val binding: ItemDiscountBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(discount: Discount) {
            val context = binding.root.context
            val currentLocale = context.resources.configuration.locales[0]
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", currentLocale)

            with(binding) {
                discountTitle.text = discount.title
                discountDescription.text = discount.description
                discountPercent.text = context.getString(R.string.discount_percent, discount.percentOff)
                discountValidity.text = context.getString(
                    R.string.discount_valid_until,
                    dateFormat.format(discount.validUntil)
                )
                applyDiscountButton.text = context.getString(R.string.apply_discount)
                applyDiscountButton.setOnClickListener { onApplyDiscount(discount) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountViewHolder {
        val binding = ItemDiscountBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DiscountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiscountViewHolder, position: Int) {
        holder.bind(discounts[position])
    }

    override fun getItemCount() = discounts.size

    fun updateDiscounts(newDiscounts: List<Discount>) {
        discounts = newDiscounts
        notifyDataSetChanged()
    }
} 
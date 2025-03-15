package com.example.pharmacyapp.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacyapp.data.model.Discount
import com.example.pharmacyapp.databinding.ItemDiscountAdminBinding
import java.text.SimpleDateFormat
import java.util.*

class DiscountsAdapter(
    private val onEditClick: (Discount) -> Unit,
    private val onDeleteClick: (Discount) -> Unit
) : RecyclerView.Adapter<DiscountsAdapter.DiscountViewHolder>() {

    private var discounts: List<Discount> = emptyList()
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    fun setDiscounts(newDiscounts: List<Discount>) {
        discounts = newDiscounts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountViewHolder {
        val binding = ItemDiscountAdminBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DiscountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiscountViewHolder, position: Int) {
        holder.bind(discounts[position])
    }

    override fun getItemCount(): Int = discounts.size

    inner class DiscountViewHolder(
        private val binding: ItemDiscountAdminBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(discount: Discount) {
            binding.discountTitle.text = discount.title
            binding.discountDescription.text = discount.description
            binding.discountPercentage.text = "${discount.percentage}% скидка"
            binding.discountValidUntil.text = "Действует до: ${dateFormat.format(Date(discount.validUntil))}"

            binding.editButton.setOnClickListener {
                onEditClick(discount)
            }

            binding.deleteButton.setOnClickListener {
                onDeleteClick(discount)
            }
        }
    }
} 
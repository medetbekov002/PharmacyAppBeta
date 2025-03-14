package com.example.pharmacyapp.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacyapp.R
import com.example.pharmacyapp.databinding.ItemPurchaseHistoryBinding
import com.example.pharmacyapp.ui.history.Purchase
import java.text.SimpleDateFormat
import java.util.Locale

class PurchaseHistoryAdapter : ListAdapter<Purchase, PurchaseHistoryAdapter.PurchaseViewHolder>(PurchaseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val binding = ItemPurchaseHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PurchaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PurchaseViewHolder(
        private val binding: ItemPurchaseHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(purchase: Purchase) {
            val context = binding.root.context
            val locale = context.resources.configuration.locales[0]
            val dateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", locale)

            binding.apply {
                orderNumberText.text = context.getString(R.string.order_number, purchase.id)
                dateText.text = context.getString(R.string.purchased_on, dateFormat.format(purchase.date))
                
                val itemsText = purchase.items.joinToString("\n") { item ->
                    "${item.name} x${item.quantity} - ${String.format(locale, "%.2f₽", item.price * item.quantity)}"
                }
                itemsListText.text = itemsText
                
                totalAmountText.text = String.format(locale, "%.2f₽", purchase.totalAmount)
            }
        }
    }

    private class PurchaseDiffCallback : DiffUtil.ItemCallback<Purchase>() {
        override fun areItemsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
            return oldItem == newItem
        }
    }
} 
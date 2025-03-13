package com.example.pharmacyapp.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacyapp.databinding.ItemPurchaseHistoryBinding
import java.text.SimpleDateFormat
import java.util.Locale

class PurchaseHistoryAdapter(private val initialPurchases: List<Purchase> = emptyList()) : 
    ListAdapter<Purchase, PurchaseHistoryAdapter.PurchaseViewHolder>(PurchaseDiffCallback()) {

    init {
        submitList(initialPurchases)
    }

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

        private val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())

        fun bind(purchase: Purchase) {
            binding.apply {
                dateText.text = dateFormat.format(purchase.date)
                totalAmountText.text = "Total: $${String.format("%.2f", purchase.totalAmount)}"
                
                val itemsText = purchase.items.joinToString("\n") { item ->
                    "${item.name} x${item.quantity} - $${String.format("%.2f", item.price * item.quantity)}"
                }
                itemsListText.text = itemsText
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
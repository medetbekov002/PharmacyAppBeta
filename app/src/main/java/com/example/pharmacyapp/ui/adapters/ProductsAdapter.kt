package com.example.pharmacyapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pharmacyapp.R
import com.example.pharmacyapp.databinding.ItemProductBinding
import com.example.pharmacyapp.data.model.Medicine

class ProductsAdapter(
    private val onProductClick: (Medicine) -> Unit
) : ListAdapter<Medicine, ProductsAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onProductClick(getItem(position))
                }
            }
        }

        fun bind(medicine: Medicine) {
            binding.apply {
                productName.text = medicine.name
                productPrice.text = "${medicine.price} ₸"
                productDescription.text = medicine.description

                // Загрузка изображения с помощью Glide
                Glide.with(productImage)
                    .load(medicine.imageUrl)
                    .placeholder(R.drawable.placeholder_product)
                    .error(R.drawable.error_product)
                    .into(productImage)
            }
        }
    }

    private class ProductDiffCallback : DiffUtil.ItemCallback<Medicine>() {
        override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem == newItem
        }
    }
} 
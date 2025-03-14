package com.example.pharmacyapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacyapp.R
import com.example.pharmacyapp.databinding.ItemProductBinding
import java.text.NumberFormat
import java.util.Locale

class ProductsAdapter(
    private var products: List<Product>,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            val context = binding.root.context
            val currentLocale = context.resources.configuration.locales[0]
            val currencyFormat = NumberFormat.getCurrencyInstance(currentLocale)

            with(binding) {
                productName.text = product.name
                productDescription.text = product.description
                productPrice.text = currencyFormat.format(product.price)
                
                // Here you would load the image using your preferred image loading library
                // Glide.with(context).load(product.imageUrl).into(productImage)
                
                addToCartButton.text = context.getString(R.string.add_to_cart)
                addToCartButton.setOnClickListener { onProductClick(product) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
} 
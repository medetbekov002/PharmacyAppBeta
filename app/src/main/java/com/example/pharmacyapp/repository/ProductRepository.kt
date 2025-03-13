package com.example.pharmacyapp.repository

import com.example.pharmacyapp.model.Product

object ProductRepository {
    val products = listOf(
        Product(
            id = 1,
            name = "Paracetamol",
            description = "Pain reliever and fever reducer",
            price = 5.99,
            imageUrl = "https://example.com/paracetamol.jpg",
            category = "Pain Relief"
        ),
        Product(
            id = 2,
            name = "Vitamin C",
            description = "Immune system support",
            price = 12.99,
            imageUrl = "https://example.com/vitamin_c.jpg",
            category = "Vitamins"
        ),
        Product(
            id = 3,
            name = "Ibuprofen",
            description = "Anti-inflammatory pain reliever",
            price = 7.99,
            imageUrl = "https://example.com/ibuprofen.jpg",
            category = "Pain Relief"
        ),
        Product(
            id = 4,
            name = "Zinc Supplements",
            description = "Immune system booster",
            price = 15.99,
            imageUrl = "https://example.com/zinc.jpg",
            category = "Supplements"
        ),
        Product(
            id = 5,
            name = "Antihistamine",
            description = "Allergy relief",
            price = 9.99,
            imageUrl = "https://example.com/antihistamine.jpg",
            category = "Allergy"
        ),
        Product(
            id = 6,
            name = "Probiotics",
            description = "Gut health support",
            price = 19.99,
            imageUrl = "https://example.com/probiotics.jpg",
            category = "Supplements"
        ),
        Product(
            id = 7,
            name = "Vitamin D3",
            description = "Bone health support",
            price = 14.99,
            imageUrl = "https://example.com/vitamin_d.jpg",
            category = "Vitamins"
        ),
        Product(
            id = 8,
            name = "Cough Syrup",
            description = "Cough relief",
            price = 8.99,
            imageUrl = "https://example.com/cough_syrup.jpg",
            category = "Cold & Flu"
        ),
        Product(
            id = 9,
            name = "First Aid Kit",
            description = "Basic medical supplies",
            price = 24.99,
            imageUrl = "https://example.com/first_aid.jpg",
            category = "First Aid"
        ),
        Product(
            id = 10,
            name = "Hand Sanitizer",
            description = "Antibacterial gel",
            price = 4.99,
            imageUrl = "https://example.com/sanitizer.jpg",
            category = "Hygiene"
        )
    )

    fun getProductsByCategory(category: String): List<Product> {
        return products.filter { it.category == category }
    }

    fun getProductById(id: Int): Product? {
        return products.find { it.id == id }
    }
} 
package com.example.pharmacyapp.repository

import com.example.pharmacyapp.model.Product

class ProductRepository {
    fun getProducts(): List<Product> {
        return listOf(
            Product(
                id = "1",
                name = "Ибупрофен",
                description = "Противовоспалительное средство",
                category = "Обезболивающие",
                price = 299.99,
                isPopular = true
            ),
            Product(
                id = "2",
                name = "Парацетамол",
                description = "Жаропонижающее средство",
                category = "Обезболивающие",
                price = 199.99
            ),
            Product(
                id = "3",
                name = "Амоксициллин",
                description = "Антибиотик широкого спектра",
                category = "Антибиотики",
                price = 599.99,
                isPopular = true
            ),
            Product(
                id = "4",
                name = "Витамин C",
                description = "Поддержка иммунитета",
                category = "Витамины",
                price = 399.99
            ),
            Product(
                id = "5",
                name = "Цитрамон",
                description = "От головной боли",
                category = "Обезболивающие",
                price = 149.99
            )
        )
    }

    fun getProductById(id: String): Product? {
        return getProducts().find { it.id == id }
    }
} 
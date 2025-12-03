package mx.edu.utez.stocktrack.data.repository

import mx.edu.utez.stocktrack.data.model.Product
import mx.edu.utez.stocktrack.data.network.ApiService

class ProductRepository(private val api: ApiService) {

    suspend fun getProducts(): List<Product> {
        val response = api.getProducts()
        return if (response.isSuccessful) response.body() ?: emptyList()
        else emptyList()
    }

    suspend fun getProductById(id: Int): Product? {
        val response = api.getProduct(id)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun insertProduct(
        name: String,
        description: String,
        amount: Int,
        date: String,
        type: String,
        imageUrl: String?
    ) {
        val product = Product(
            name = name,
            description = description,
            amount = amount,
            date = date,
            type = type,
            imageUrl = imageUrl
        )
        api.createProduct(product)
    }

    suspend fun updateProduct(
        id: Int?,
        name: String,
        description: String,
        amount: Int,
        date: String,
        type: String,
        imageUrl: String?
    ) {
        val product = Product(
            id = id,
            name = name,
            description = description,
            amount = amount,
            date = date,
            type = type,
            imageUrl = imageUrl
        )
        api.updateProduct(id, product)
    }

    suspend fun deleteProduct(id: Int?) {
        api.deleteProduct(id)
    }
}

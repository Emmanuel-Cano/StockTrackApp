package mx.edu.utez.stocktrack.data.repository

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.edu.utez.stocktrack.data.model.Product
import mx.edu.utez.stocktrack.data.network.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProductRepository(private val api: ApiService) {

    suspend fun getProducts(): List<Product>? {
        return withContext(Dispatchers.IO) {
            val response = api.getProducts()
            if (response.isSuccessful) response.body() else null
        }
    }

    suspend fun createProduct(
        context: Context,
        imageUri: Uri?,
        name: String,
        description: String,
        amount: Int,
        date: String,
        type: String
    ): Product? {
        val imagePart = prepareImage(context, imageUri)

        val response = api.createProduct(
            image = imagePart,
            name = name.toRB(),
            description = description.toRB(),
            amount = amount.toString().toRB(),
            date = date.toRB(),
            type = type.toRB()
        )
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updateProduct(
        context: Context,
        id: Int,
        imageUri: Uri?,
        name: String,
        description: String,
        amount: Int,
        date: String,
        type: String
    ): Product? {
        val imagePart = prepareImage(context, imageUri)

        val response = api.updateProduct(
            id = id,
            image = imagePart,
            name = name.toRB(),
            description = description.toRB(),
            amount = amount.toString().toRB(),
            date = date.toRB(),
            type = type.toRB()
        )
        return if (response.isSuccessful) response.body() else null
    }

    /** Eliminar un producto por id */
    suspend fun deleteProduct(id: Int): Boolean {
        val response = api.deleteProduct(id)
        return response.isSuccessful
    }


    // ======================================
    // 🚀 PREPARAR IMAGEN (100% Funcional)
    // ======================================
    private fun prepareImage(context: Context, uri: Uri?): MultipartBody.Part? {
        if (uri == null) return null

        val inputStream = context.contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: return null

        val requestFile = bytes.toRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(
            "image",
            "product_${System.currentTimeMillis()}.jpg",
            requestFile
        )
    }

    // String → RequestBody
    private fun String.toRB(): RequestBody =
        this.toRequestBody("text/plain".toMediaTypeOrNull())
}

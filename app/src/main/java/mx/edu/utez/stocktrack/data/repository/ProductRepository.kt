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

    suspend fun getProducts(): List<Product>? = withContext(Dispatchers.IO) {
        val response = api.getProducts()
        if (response.isSuccessful) response.body() else null
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
            image = imagePart, // si imageUri es URL remoto, será null y no se reenvía
            name = name.toRB(),
            description = description.toRB(),
            amount = amount.toString().toRB(),
            date = date.toRB(),
            type = type.toRB()
        )
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun deleteProduct(id: Int) {
        withContext(Dispatchers.IO) {
            api.deleteProduct(id)
        }
    }

    /**
     * Prepara la imagen solo si es un URI local (content:// o file://)
     * Ignora URLs remotas (http:// o https://) para no causar crash
     */
    private fun prepareImage(context: Context, uri: Uri?): MultipartBody.Part? {
        if (uri == null) return null

        val uriString = uri.toString()
        if (uriString.startsWith("http://") || uriString.startsWith("https://")) {
            // Imagen remota, no se reenvía
            return null
        }

        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val bytes = inputStream.readBytes()
        val requestFile = bytes.toRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(
            "image",
            "product_${System.currentTimeMillis()}.jpg",
            requestFile
        )
    }

    private fun String.toRB(): RequestBody = this.toRequestBody("text/plain".toMediaTypeOrNull())
}

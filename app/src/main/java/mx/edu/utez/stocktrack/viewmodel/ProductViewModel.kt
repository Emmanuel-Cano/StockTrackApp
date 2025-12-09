package mx.edu.utez.stocktrack.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.stocktrack.data.model.Product
import mx.edu.utez.stocktrack.data.repository.ProductRepository

class ProductViewModel(private val repo: ProductRepository) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    // Estado de carga
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage


    fun loadProducts() {
        viewModelScope.launch {
            safeRequest {
                val data = repo.getProducts()
                if (data != null) _products.value = data
            }
        }
    }

    fun createProduct(
        context: Context,
        imageUri: Uri?,
        name: String,
        description: String,
        amount: String,
        date: String,
        type: String,
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            safeRequest {
                val amountInt = amount.toIntOrNull() ?: 0

                repo.createProduct(
                    context,
                    imageUri,
                    name.trim(),
                    description.trim(),
                    amountInt,
                    date.trim(),
                    type.trim()
                )

                loadProducts()
                onDone()
            }
        }
    }
    fun updateProduct(
        context: Context,
        id: Int,
        imageUri: Uri?,
        name: String,
        description: String,
        amount: String,
        date: String,
        type: String,
        onDone: () -> Unit,
    ) {
        viewModelScope.launch {
            safeRequest {
                val amountInt = amount.toIntOrNull() ?: 0

                repo.updateProduct(
                    context,
                    id,
                    imageUri,
                    name.trim(),
                    description.trim(),
                    amountInt,
                    date.trim(),
                    type.trim()
                )

                loadProducts()
                onDone()
            }
        }
    }

    fun deleteProduct(
        id: Int,
        onDone: () -> Unit = {}
    ) {
        viewModelScope.launch {
            safeRequest {
                repo.deleteProduct(id)
                loadProducts()
                onDone()
            }
        }
    }

    private suspend fun safeRequest(block: suspend () -> Unit) {
        try {
            _loading.value = true
            _errorMessage.value = null
            block()
        } catch (e: Exception) {
            _errorMessage.value = e.message ?: "Error inesperado"
            e.printStackTrace()
        } finally {
            _loading.value = false
        }
    }
}

class ProductViewModelFactory(private val repo: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

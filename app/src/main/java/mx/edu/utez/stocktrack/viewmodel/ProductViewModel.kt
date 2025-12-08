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

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = true
            val data = repo.getProducts()
            if (data != null) _products.value = data
            _loading.value = false
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
            _loading.value = true
            val amountInt = amount.toIntOrNull() ?: 0
            repo.createProduct(context, imageUri, name, description, amountInt, date, type)
            loadProducts()
            _loading.value = false
            onDone()
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
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            _loading.value = true
            val amountInt = amount.toIntOrNull() ?: 0
            repo.updateProduct(context, id, imageUri, name, description, amountInt, date, type)
            loadProducts()
            _loading.value = false
            onDone()
        }
    }

    fun deleteProduct(id: Int, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            _loading.value = true
            repo.deleteProduct(id)
            loadProducts()
            _loading.value = false
            onDone()
        }
    }
}

/* ======================================================
   FACTORY PARA INYECTAR REPOSITORY
   ====================================================== */
class ProductViewModelFactory(private val repo: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

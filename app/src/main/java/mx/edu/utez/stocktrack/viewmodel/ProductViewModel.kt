package mx.edu.utez.stocktrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.stocktrack.data.model.Product
import mx.edu.utez.stocktrack.data.repository.ProductRepository

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _products.value = repository.getProducts()
            } catch (e: Exception) {
                _error.value = "Error cargando productos: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getProduct(id: Int, onResult: (Product?) -> Unit) {
        viewModelScope.launch {
            try {
                val product = repository.getProductById(id)
                onResult(product)
            } catch (e: Exception) {
                _error.value = "Error obteniendo producto: ${e.message}"
                onResult(null)
            }
        }
    }

    fun insertProduct(
        name: String,
        description: String,
        amount: Int,
        date: String,
        type: String,
        imageUrl: String?
    ) {
        viewModelScope.launch {
            try {
                repository.insertProduct(name, description, amount, date, type, imageUrl)
                loadProducts()
            } catch (e: Exception) {
                _error.value = "Error insertando producto: ${e.message}"
            }
        }
    }

    fun updateProduct(
        id: Int?,
        name: String,
        description: String,
        amount: Int,
        date: String,
        type: String,
        imageUrl: String?
    ) {
        viewModelScope.launch {
            try {
                repository.updateProduct(id, name, description, amount, date, type, imageUrl)
                loadProducts()
            } catch (e: Exception) {
                _error.value = "Error actualizando producto: ${e.message}"
            }
        }
    }

    fun deleteProduct(id: Int?) {
        viewModelScope.launch {
            try {
                repository.deleteProduct(id)
                loadProducts()
            } catch (e: Exception) {
                _error.value = "Error eliminando producto: ${e.message}"
            }
        }
    }
}

class ProductViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}

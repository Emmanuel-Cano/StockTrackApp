package mx.edu.utez.stocktrack.data.model

data class Product (
    val id: Int? = null,
    val name: String,
    val description: String,
    val amount: Int,
    val date: String,
    val type: String,
    val imageUrl: String?
)

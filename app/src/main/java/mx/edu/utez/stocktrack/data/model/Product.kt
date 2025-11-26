package mx.edu.utez.stocktrack.data.model

data class Product (
    val name: String,
    val desciption: String,
    val amount: Int,
    val date: String,
    val type: String,
    val imageUrl: String?

)
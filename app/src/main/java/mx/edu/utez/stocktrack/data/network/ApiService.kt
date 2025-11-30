package mx.edu.utez.stocktrack.data.network

import mx.edu.utez.stocktrack.data.model.LoginRequest
import mx.edu.utez.stocktrack.data.model.Product
import mx.edu.utez.stocktrack.data.model.UserRequest
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // --------------------
    // PRODUCTOS
    // --------------------
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Response<Product>

    @POST("products")
    suspend fun createProduct(@Body product: Product): Response<Product>

    @PUT("products/{id}")
    suspend fun updateProduct(@Path("id") id: Int, @Body product: Product): Response<Product>

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Response<Map<String, String>>

    // --------------------
    // USUARIO
    // --------------------
    @POST("api/usuarios/registro")
    suspend fun registrarUsuario(@Body user: UserRequest): Response<Void>

    @POST("api/usuarios/login")
    suspend fun loginUsuario(@Body credentials: LoginRequest): Response<Void>
}

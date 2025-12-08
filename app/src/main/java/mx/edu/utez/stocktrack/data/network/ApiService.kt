package mx.edu.utez.stocktrack.data.network

import mx.edu.utez.stocktrack.data.model.LoginRequest
import mx.edu.utez.stocktrack.data.model.UserRequest
import mx.edu.utez.stocktrack.data.model.Product
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("auth/register")
    suspend fun register(@Body request: UserRequest): Response<Map<String, Any>>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<Map<String, Any>>

    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Response<Product>

    @Multipart
    @POST("products")
    suspend fun createProduct(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("amount") amount: RequestBody,
        @Part("date") date: RequestBody,
        @Part("type") type: RequestBody
    ): Response<Product>

    @Multipart
    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("amount") amount: RequestBody,
        @Part("date") date: RequestBody,
        @Part("type") type: RequestBody
    ): Response<Product>

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Response<Map<String, String>>
}

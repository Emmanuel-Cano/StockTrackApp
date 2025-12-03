package mx.edu.utez.stocktrack.viewmodel


import mx.edu.utez.stocktrack.data.model.LoginRequest
import mx.edu.utez.stocktrack.data.model.UserRequest
import mx.edu.utez.stocktrack.data.network.ApiService


class UserRepository(private val api: ApiService) {
    suspend fun registrarUsuario(user: UserRequest): Result<String> {
        return try {
            print(user)
            val response = api.register(user)
            if (response.isSuccessful) {
                Result.success("Usuario registrado con éxito")
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            val request = LoginRequest(email, password)
            val response = api.login(request)

            if (response.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Exception("Credenciales incorrectas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
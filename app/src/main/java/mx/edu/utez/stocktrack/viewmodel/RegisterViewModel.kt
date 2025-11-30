package mx.edu.utez.stocktrack.viewmodel


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.stocktrack.data.model.UserRequest
import mx.edu.utez.stocktrack.data.network.RetrofitInstance

class RegisterViewModel() : ViewModel() {
    private val repository = UserRepository(RetrofitInstance.api)

    // Estados para los campos de texto
    var nombre by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordc by mutableStateOf("")

    // Estado para la UI (Carga y mensajes)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var successMessage by mutableStateOf<String?>(null)
    var isRegisterSuccess by mutableStateOf(false)

    fun onRegisterClick() {
        // 1. Validaciones Locales
        if (nombre.isBlank() || email.isBlank() || password.isBlank()) {
            errorMessage = "Todos los campos son obligatorios"
            return
        }
        if (password != passwordc) {
            errorMessage = "Las contraseñas no coinciden"
            return
        }

        // 2. Preparar datos y llamar al repo
        isLoading = true
        errorMessage = null
        successMessage = null
        isRegisterSuccess = false

        val newUser = UserRequest(
            nombre = nombre,
            email = email,
            password = password,
            passwordc = passwordc
        )

        viewModelScope.launch {
            val result = repository.registrarUsuario(newUser)
            isLoading = false
            result.onSuccess { msg ->
                successMessage = msg
                limpiarFormulario()
                isRegisterSuccess = true
            }.onFailure { error ->
                errorMessage = error.message ?: "Error desconocido"
            }
        }
    }

    private fun limpiarFormulario() {
        nombre = ""
        email = ""
        password = ""
        passwordc = ""

        Log.e("Hola", "Hubo un problema al limipar el formulario")
    }
}
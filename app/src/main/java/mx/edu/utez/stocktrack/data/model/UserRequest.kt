package mx.edu.utez.stocktrack.data.model

import com.google.gson.annotations.SerializedName

data class UserRequest(

    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("passwordc") val passwordc: String
)
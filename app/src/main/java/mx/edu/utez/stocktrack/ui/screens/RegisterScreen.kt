package mx.edu.utez.stocktrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.stocktrack.viewmodel.RegisterViewModel


@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegistrationSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {

    LaunchedEffect(viewModel.isRegisterSuccess) {
        if (viewModel.isRegisterSuccess) {
            onRegistrationSuccess()
            viewModel.isRegisterSuccess = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Crear Cuenta",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier =  Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Ingresa los datos solicitados",
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            color = Color.Black,
            modifier =  Modifier.align(Alignment.CenterHorizontally)
        )


        Spacer(modifier = Modifier.height(16.dp))

        // Nombre
        OutlinedTextField(
            value = viewModel.nombre,
            onValueChange = { viewModel.nombre = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )



        // Email
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Correo") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Contraseña
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        // Repetir Contraseña
        OutlinedTextField(
            value = viewModel.passwordc,
            onValueChange = { viewModel.passwordc = it },
            label = { Text("Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.password != viewModel.passwordc && viewModel.passwordc.isNotEmpty(),
            supportingText = {
                if (viewModel.password != viewModel.passwordc && viewModel.passwordc.isNotEmpty()) {
                    Text("Las contraseñas no coinciden")
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (viewModel.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { viewModel.onRegisterClick() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear cuenta")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { onNavigateBack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al Login")
            }
        }

        // Mensajes de error/éxito
        viewModel.errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
        viewModel.successMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.primary)
        }
    }
}
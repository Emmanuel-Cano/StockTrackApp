package mx.edu.utez.stocktrack.data.model

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.unit.sp

@Composable
fun RestablecerContrasenaScreen() {
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "Restablecer\ncontraseña",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            lineHeight = 40.sp,
            fontSize = 42.sp,
            letterSpacing = 1.5.sp,
            color = Color(0xFF666666)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Ingresa una nueva contraseña",
            color = Color(0xFF666666),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(68.dp))

        Text(
            "Contraseña",
            color = Color(0xFF666666),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            placeholder = {
                Text("Ingresa tu contraseña", color = Color(0xFF999999))
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            "Confirmar tu contraseña",
            color = Color(0xFF666666),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = confirmarContrasena,
            onValueChange = { confirmarContrasena = it },
            placeholder = {
                Text("Confirma tu contraseña", color = Color(0xFF999999))
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8B5A2B),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Text(
                "Guardar",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(52.dp))

        TextButton(onClick = {}) {
            Text("Iniciar sesión",fontSize = 22.sp,
                color = Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestablecerContrasenaPreview() {
    MaterialTheme {
        RestablecerContrasenaScreen()
    }
}

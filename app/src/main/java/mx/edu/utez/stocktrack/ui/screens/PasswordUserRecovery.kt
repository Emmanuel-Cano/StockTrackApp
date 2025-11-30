package mx.edu.utez.stocktrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.stocktrack.ui.components.inputs.PasswordField
import mx.edu.utez.stocktrack.ui.components.texts.Link
import mx.edu.utez.stocktrack.viewmodel.LoginViewModel

@Composable
fun RecuperarUserContrasenaScreen(
    viewModel: LoginViewModel,
    navController: NavController,
    onRecuperarClick: () -> Unit,
    onCancelarClick: () -> Unit
) {

    var correo by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCancelarClick) {
                    Text("Cancelar", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Restablecer contraseña",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Ingresa una nueva contraseña",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Contraseña",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.align(Alignment.Start)
            )

            PasswordField(
                viewModel = viewModel,
                label = "Contraseña"
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Contraseña",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.align(Alignment.Start)
            )

            PasswordField(
                viewModel = viewModel,
                label = "Confirmar contraseña"
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = onRecuperarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentButton)
            ) {
                Text(
                    text = "Continuar",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Link("Iniciar sesión") {
                navController.navigate("loginUser")
            }
        }
    }
}

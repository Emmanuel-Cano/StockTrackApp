package mx.edu.utez.stocktrack.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.stocktrack.R

@Composable
fun RecuperarContrasenaScreen(
    onRecuperarClick: () -> Unit,
    onCancelarClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        var correo by remember { mutableStateOf("") }

        Box(
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onCancelarClick) {
                        Text("Cancelar", color = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = "Recuperar Cuenta",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier =  Modifier.align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "Ingresa tu nombre de usuario",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Black,
                    modifier =  Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(12.dp))


                Spacer(modifier = Modifier.height(40.dp))


                Text(
                    text = "Nombre de usuario",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.align(Alignment.Start)
                )

                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(
                            "Nombre de usuario",
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))

                Image(
                    painter = painterResource(id = R.drawable.recuperar),
                    contentDescription = "Imagen bienvenida",
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .size(250.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = onRecuperarClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentButton)
                ) {
                    Text(
                        text = "Continuar",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun RecuperarContrasenaScreenPreview() {
    RecuperarContrasenaScreen(
        onRecuperarClick = {},
        onCancelarClick = {}
    )
}

package mx.edu.utez.stocktrack.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.stocktrack.ui.components.buttons.PrimaryButton
import mx.edu.utez.stocktrack.ui.components.texts.Title
import mx.edu.utez.stocktrack.viewmodel.LoginViewModel
import mx.edu.utez.stocktrack.R
import mx.edu.utez.stocktrack.ui.components.buttons.SecundaryButton

@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavController){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
        ) {
            Title("StockTrack")

            Text(
                text = "Bienvenido",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFAC7F5E),
                modifier =  Modifier.align(Alignment.CenterHorizontally)
            )

            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Imagen bienvenida",
                modifier = Modifier
                    .padding(top = 30.dp)
                    .size(250.dp)
            )

            PrimaryButton("Iniciar Sesión") {
                navController.navigate("loginUser")
            }


            SecundaryButton("Registrarse") {
                navController.navigate("register")
            }

        }
    }
}


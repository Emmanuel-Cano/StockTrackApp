package mx.edu.utez.stocktrack.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.stocktrack.ui.screens.LoginScreen
import mx.edu.utez.stocktrack.ui.screens.LoginUserScreen
import mx.edu.utez.stocktrack.ui.screens.RecuperarContrasenaScreen
import mx.edu.utez.stocktrack.ui.screens.RecuperarUserContrasenaScreen
import mx.edu.utez.stocktrack.viewmodel.LoginViewModel


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(loginViewModel, navController)
        }

        composable("loginUser") {
            LoginUserScreen(loginViewModel, navController)
        }


        composable("password") {
            RecuperarContrasenaScreen(
                onRecuperarClick = { navController.popBackStack() },
                onCancelarClick = { navController.popBackStack() }
            )

        }
        composable("passworduser") {
            RecuperarUserContrasenaScreen(
                viewModel = loginViewModel,
                navController = navController,
                onRecuperarClick = { navController.popBackStack() },
                onCancelarClick = { navController.popBackStack() }
            )
        }

    }
}
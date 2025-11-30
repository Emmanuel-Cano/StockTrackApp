package mx.edu.utez.stocktrack.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.stocktrack.ui.screens.*
import mx.edu.utez.stocktrack.viewmodel.LoginViewModel
import mx.edu.utez.stocktrack.viewmodel.RegisterViewModel

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

        composable("register") {
            val registerViewModel: RegisterViewModel = viewModel()

            RegisterScreen(
                viewModel = registerViewModel,
                onRegistrationSuccess = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

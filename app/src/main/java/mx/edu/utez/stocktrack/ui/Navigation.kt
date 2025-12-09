package mx.edu.utez.stocktrack.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.edu.utez.stocktrack.data.network.RetrofitInstance
import mx.edu.utez.stocktrack.data.repository.ProductRepository
import mx.edu.utez.stocktrack.ui.screens.*
import mx.edu.utez.stocktrack.viewmodel.LoginViewModel
import mx.edu.utez.stocktrack.viewmodel.ProductViewModel
import mx.edu.utez.stocktrack.viewmodel.ProductViewModelFactory
import mx.edu.utez.stocktrack.viewmodel.RegisterViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val loginViewModel: LoginViewModel = viewModel()

    val repository = ProductRepository(RetrofitInstance.api)
    val factory = ProductViewModelFactory(repository)

    val productViewModel: ProductViewModel = viewModel(factory = factory)

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                navController = navController
            )
        }

        composable("loginUser") {
            LoginUserScreen(
                viewModel = loginViewModel,
                navController = navController,
                onLoginSuccess = {
                    navController.navigate("inventory") {
                        popUpTo("loginUser") { inclusive = true }
                    }
                }
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

        composable("inventory") {
            InventoryScreen(
                navController = navController,
                viewModel = productViewModel,
                onAddProductClick = { navController.navigate("addProduct") },
                onLogoutClick = {
                    loginViewModel.clearSession()
                    navController.navigate("login") {
                        popUpTo("inventory") { inclusive = true }
                    }
                }
            )
        }

        composable("addProduct") {
            AddProductScreen(
                viewModel = productViewModel,
                onFinish = { navController.popBackStack() },
                onLogoutClick = {
                    loginViewModel.clearSession()
                    navController.navigate("login"){
                        popUpTo("addProduct"){
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("recuperar") {
            RecuperarContrasenaScreen(
                onRecuperarClick = {
                },
                onCancelarClick = {
                    navController.popBackStack()
                }
            )
        }


        composable(
            "updateProduct/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id") ?: 0

            AddProductScreen(
                viewModel = productViewModel,
                productId = id,
                onFinish = { navController.popBackStack() },
                onLogoutClick = {
                    loginViewModel.clearSession()
                    navController.navigate("login"){
                        popUpTo("addProduct"){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

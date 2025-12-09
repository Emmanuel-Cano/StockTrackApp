package mx.edu.utez.stocktrack.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.delay
import mx.edu.utez.stocktrack.ui.components.ProductCard
import mx.edu.utez.stocktrack.R
import mx.edu.utez.stocktrack.data.network.RetrofitInstance
import mx.edu.utez.stocktrack.data.repository.ProductRepository
import mx.edu.utez.stocktrack.viewmodel.ProductViewModel
import mx.edu.utez.stocktrack.viewmodel.ProductViewModelFactory

@Composable
fun InventoryScreen(
    navController: NavController,
    viewModel: ProductViewModel,
    onAddProductClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val cafe = Color(0xFFA88871)

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.loadProducts()
            delay(30000) // 5 segundos
        }
    }



    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.loading.collectAsState()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.banner),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = "StockTrack",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 28.sp,
                                letterSpacing = 1.5.sp
                            )
                        )
                        Text(
                            text = "Inventario",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 22.sp,
                                letterSpacing = 1.5.sp
                            )
                        )
                    }

                    IconButton(onClick = { onLogoutClick() }) {
                        Icon(
                            painter = painterResource(R.drawable.logout),
                            contentDescription = "Cerrar sesión",
                            tint = Color.White
                        )
                    }
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(cafe),
                contentAlignment = Alignment.Center
            ) {
                FloatingActionButton(
                    onClick = { onAddProductClick() },
                    containerColor = Color.White,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = "Agregar",
                        tint = cafe,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(24.dp))

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(products) { product ->
                        ProductCard(
                            product = product,
                            viewModel = viewModel,
                            onUpdateClick = { selectedProduct ->
                                // Navegar a pantalla de actualización
                                navController.navigate("updateProduct/${selectedProduct.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}


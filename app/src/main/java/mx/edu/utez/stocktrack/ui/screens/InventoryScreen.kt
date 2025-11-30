package mx.edu.utez.stocktrack.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.stocktrack.R

@Composable
fun InventoryScreen() {

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
                        Text("StockTrack",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge, fontSize = 28.sp,
                            letterSpacing = 1.5.sp,

                        )
                        Text(
                            text = "Inventario",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge, fontSize = 22.sp,
                            letterSpacing = 1.5.sp,
                        )
                    }

                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.logout),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
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

            SearchInput()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Contenido inventario",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun SearchInput() {
    var searchText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Search, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Buscar producto")
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(30.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryScreenPreview() {
    MaterialTheme {
        InventoryScreen()
    }
}

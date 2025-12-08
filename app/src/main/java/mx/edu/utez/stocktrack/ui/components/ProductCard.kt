package mx.edu.utez.stocktrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.edu.utez.stocktrack.data.model.Product
import mx.edu.utez.stocktrack.viewmodel.ProductViewModel

@Composable
fun ProductCard(
    product: Product,
    viewModel: ProductViewModel,
    onUpdateClick: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(8.dp)
                    ),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(product.name, fontSize = 18.sp)
                    Text(product.description, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Cantidad: ${product.amount}", fontSize = 12.sp)
                    Text("Fecha registro: ${product.date}", fontSize = 12.sp)
                    Text("Tipo: ${product.type}", fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.width(10.dp))

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(100.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.height(100.dp)
                ) {
                    // Botón Eliminar
                    Button(
                        onClick = {
                            product.id?.let { id ->
                                // Lanza la coroutine para eliminar producto
                                CoroutineScope(Dispatchers.IO).launch {
                                    viewModel.deleteProduct(id)
                                }
                            }
                        }
                    ) {
                        Text("Eliminar", fontSize = 14.sp)
                    }

                    // Botón Actualizar
                    Button(onClick = { onUpdateClick(product) }) {
                        Text("Actualizar", fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

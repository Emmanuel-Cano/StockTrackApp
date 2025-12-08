package mx.edu.utez.stocktrack.ui.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import mx.edu.utez.stocktrack.viewmodel.ProductViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable

fun AddProductScreen(
    viewModel: ProductViewModel,
    productId: Int? = null,
    onFinish: () -> Unit
) {

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // ---- URI temporal para cámara ----
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    // ---- Lanzador para Galería ----
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    // ---- Lanzador para Cámara ----
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri = tempUri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Agregar Producto", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        // ------- Botón cámara -------
        Button(onClick = {
            // Crear archivo temporal
            val uri = createTempImageUri(context)
            tempUri = uri
            // Lanzar cámara solo si URI no es null
            uri?.let {
                cameraLauncher.launch(it)
            }
        }) {
            Text("Tomar Foto")
        }


        Spacer(Modifier.height(10.dp))

        // ------- Botón galería -------
        Button(onClick = { galleryLauncher.launch("image/*") }) {
            Text("Seleccionar de Galería")
        }

        Spacer(Modifier.height(10.dp))

        // Mostrar imagen
        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })
        OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Cantidad") })
        OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Fecha") })
        OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Tipo") })

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.createProduct(
                    context = context,
                    imageUri = imageUri,
                    name = name,
                    description = description,
                    amount = amount,
                    date = date,
                    type = type,
                ) {
                    onFinish()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}



private fun createTempImageUri(context: Context): Uri? {
    return try {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFile = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "IMG_$timestamp.jpg"
        )
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            imageFile
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

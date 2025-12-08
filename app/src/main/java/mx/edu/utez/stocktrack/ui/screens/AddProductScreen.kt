package mx.edu.utez.stocktrack.ui.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
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
import coil.compose.rememberAsyncImagePainter
import mx.edu.utez.stocktrack.viewmodel.ProductViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.FileProvider

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
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    // Cargar productos si no están cargados
    LaunchedEffect(Unit) { if (viewModel.products.value.isEmpty()) viewModel.loadProducts() }

    // Llenar campos cuando productos estén listos y haya productId
    LaunchedEffect(viewModel.products.value, productId) {
        productId?.let { id ->
            val product = viewModel.products.value.find { it.id == id }
            product?.let {
                name = it.name
                description = it.description
                amount = it.amount.toString()
                date = it.date
                type = it.type
                imageUri = it.imageUrl?.let { url -> Uri.parse(url) }
            }
        }
    }

    // Lanzador para galería
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    // Lanzador para cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) imageUri = tempUri
    }

    // Lanzador para permisos de cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = createTempImageUri(context)
            tempUri = uri
            uri?.let { cameraLauncher.launch(it) }
        } else {
            Toast.makeText(context, "Permiso de cámara requerido", Toast.LENGTH_SHORT).show()
        }
    }

    // Lanzador para permisos de lectura de imágenes (galería)
    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) galleryLauncher.launch("image/*")
        else Toast.makeText(context, "Permiso de almacenamiento requerido", Toast.LENGTH_SHORT).show()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            if (productId == null) "Agregar Producto" else "Actualizar Producto",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(16.dp))

        // Botón cámara
        Button(onClick = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) }) {
            Text("Tomar Foto")
        }

        Spacer(Modifier.height(10.dp))

        // Botón galería
        Button(onClick = { galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES) }) {
            Text("Seleccionar de Galería")
        }

        Spacer(Modifier.height(10.dp))

        // Mostrar imagen seleccionada
        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally)
            )
        }

        Spacer(Modifier.height(20.dp))

        // Campos de texto
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })
        OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Cantidad") })
        OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Fecha") })
        OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Tipo") })

        Spacer(Modifier.height(20.dp))

        // Botón Guardar / Actualizar
        Button(
            onClick = {
                if (productId == null) {
                    viewModel.createProduct(context, imageUri, name, description, amount, date, type) { onFinish() }
                } else {
                    viewModel.updateProduct(context, productId, imageUri, name, description, amount, date, type) { onFinish() }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (productId == null) "Guardar" else "Actualizar")
        }
    }
}

private fun createTempImageUri(context: Context): Uri? {
    return try {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFile = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "IMG_$timestamp.jpg")
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", imageFile)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

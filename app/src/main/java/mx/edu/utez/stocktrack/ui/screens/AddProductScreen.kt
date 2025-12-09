package mx.edu.utez.stocktrack.ui.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import mx.edu.utez.stocktrack.viewmodel.ProductViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.FileProvider
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import mx.edu.utez.stocktrack.R

@Composable
fun AddProductScreen(
    viewModel: ProductViewModel,
    productId: Int? = null,
    onFinish: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    val scrollState = rememberScrollState()
    val cafe = Color(0xFFA88871)

    LaunchedEffect(Unit) { if (viewModel.products.value.isEmpty()) viewModel.loadProducts() }
    LaunchedEffect(productId, viewModel.products.value) {
        if (productId != null && viewModel.products.value.isNotEmpty()) {
            val product = viewModel.products.value.find { it.id == productId }
            product?.let {
                name = it.name
                description = it.description
                amount = it.amount.toString()
                date = it.date
                type = it.type
                imageUri = it.imageUrl?.let(Uri::parse)
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? -> imageUri = uri }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success -> if (success) imageUri = tempUri }
    val cameraPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) createTempImageUri(context)?.let { tempUri = it; cameraLauncher.launch(it) }
        else Toast.makeText(context, "Permiso de cámara requerido", Toast.LENGTH_SHORT).show()
    }
    val galleryPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) galleryLauncher.launch("image/*") else Toast.makeText(context, "Permiso requerido para galería", Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.banner),
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
                        Text("StockTrack", color = Color.White, style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp, letterSpacing = 1.5.sp))
                        Text(if (productId == null) "Agregar Producto" else "Actualizar Producto", color = Color.White, style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp, letterSpacing = 1.5.sp))
                    }
                    IconButton(onClick = { onLogoutClick() }) {
                        Icon(painter = painterResource(id = R.drawable.logout), contentDescription = "Cerrar sesión", tint = Color.White)
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
            ) {}
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text("Imagen del producto", fontSize = 16.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(4.dp))
            imageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
            } ?: Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) { Text("No hay imagen", color = Color.DarkGray) }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { cameraPermission.launch(Manifest.permission.CAMERA) },
                    modifier = Modifier.weight(1f).height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5E3C))
                ) { Text("Tomar foto", fontSize = 12.sp) }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { galleryPermission.launch(Manifest.permission.READ_MEDIA_IMAGES) },
                    modifier = Modifier.weight(1f).height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5E3C))
                ) { Text("Galería", fontSize = 12.sp) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Cantidad") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Fecha") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Tipo") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (productId == null) {
                        viewModel.createProduct(context, imageUri, name, description, amount, date, type) { onFinish() }
                    } else {
                        viewModel.updateProduct(context, productId, imageUri, name, description, amount, date, type) { onFinish() }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5E3C))
            ) { Text(if (productId == null) "Guardar" else "Actualizar", fontSize = 16.sp) }

            Spacer(modifier = Modifier.height(32.dp))
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

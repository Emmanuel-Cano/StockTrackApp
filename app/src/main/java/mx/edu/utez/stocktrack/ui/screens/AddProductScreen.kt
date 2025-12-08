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
    viewModel: ProductViewModel = viewModel(),
    productId: Int? = null,
    onProductSaved: () -> Unit = {}
) {
    val context = LocalContext.current
    val isUpdateMode = productId != null
    val products by viewModel.products.collectAsState()

    val productToEdit = productId?.let { id -> products.find { it.id == id } }

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(productToEdit) {
        productToEdit?.let {
            name = it.name
            description = it.description
            amount = it.amount.toString()
            date = it.date
            type = it.type
            imageUri = it.imageUrl?.let { Uri.parse(it) }
        }
    }

    val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arrayOf(Manifest.permission.CAMERA) else arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    var hasPermissions by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        hasPermissions = results.values.all { it }
    }
    LaunchedEffect(Unit) {
        hasPermissions = requiredPermissions.all { ContextCompat.checkSelfPermission(context, it) == android.content.pm.PackageManager.PERMISSION_GRANTED }
        if (!hasPermissions) permissionLauncher.launch(requiredPermissions)
    }

    val launcherCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) imageUri = tempImageUri
        tempImageUri = null
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {



        Text(if (isUpdateMode) "Actualizar producto" else "Agregar producto", style = MaterialTheme.typography.headlineSmall)
        Text("Nombre", style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        Text("Descripción", style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })
        Text("Cantidad", style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Cantidad") })
        Text("Fecha", style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Fecha (YYYY-MM-DD)") })
        Text("Tipo", style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Tipo") })

        imageUri?.let { uri ->
            Image(painter = rememberAsyncImagePainter(uri), contentDescription = "Foto del producto", modifier = Modifier.size(180.dp).align(Alignment.CenterHorizontally))
        }

        Button(
            onClick = {
                if (!hasPermissions) permissionLauncher.launch(requiredPermissions)
                else {
                    val uri = createTempImageUri(context)
                    tempImageUri = uri
                    launcherCamera.launch(uri)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tomar foto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isUpdateMode && productToEdit != null) {
                    viewModel.updateProduct(
                        id = productToEdit.id,
                        name = name,
                        description = description,
                        amount = amount.toIntOrNull() ?: 0,
                        date = date,
                        type = type,
                        imageUrl = imageUri?.toString()
                    )
                } else {
                    viewModel.insertProduct(
                        name = name,
                        description = description,
                        amount = amount.toIntOrNull() ?: 0,
                        date = date,
                        type = type,
                        imageUrl = imageUri?.toString()
                    )
                }
                onProductSaved()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isUpdateMode) "Actualizar producto" else "Guardar producto")
        }
    }
}

private fun createTempImageUri(context: Context): Uri {
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val imageFile = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "IMG_$timestamp.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", imageFile)
}

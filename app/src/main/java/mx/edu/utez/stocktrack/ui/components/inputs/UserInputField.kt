package mx.edu.utez.stocktrack.ui.components.inputs


import android.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import mx.edu.utez.stocktrack.viewmodel.LoginViewModel

@Composable
fun UserInputField(
    viewModel: LoginViewModel,
    label: String = "Usuario/correo",
    onNext: (() -> Unit)? = null
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = viewModel.username.value,
        onValueChange = { viewModel.username.value = it },
        label = { Text(label,
            color = Color(0xFFAFAFAF)
        ) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                onNext?.invoke() ?: focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Down)
            }
        )
    )
}
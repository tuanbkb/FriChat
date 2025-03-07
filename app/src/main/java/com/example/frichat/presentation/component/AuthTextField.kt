package com.example.frichat.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frichat.R
import com.example.frichat.ui.theme.AppTheme

@Composable
fun AuthTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    modifier: Modifier = Modifier
) {
    val showPassword = if (isPassword) remember { mutableStateOf(false) } else null
    OutlinedTextField(
        label = { Text(text = label) },
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        shape = MaterialTheme.shapes.extraLarge,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction
        ),
        trailingIcon = {
            if (isPassword) {
                IconButton(
                    onClick = { showPassword!!.value = !showPassword.value }
                ) {
                    if (!showPassword!!.value)
                        Icon(
                            painter = painterResource(R.drawable.visibility_off),
                            contentDescription = "Toggle Password Visibility"
                        )
                    else
                        Icon(
                            painter = painterResource(R.drawable.visibility),
                            contentDescription = "Toggle Password Visibility"
                        )
                }
            }
        },
        visualTransformation = if (isPassword && !showPassword!!.value)
            PasswordVisualTransformation('*')
        else VisualTransformation.None,
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp)
    )
}

@Preview
@Composable
fun AuthTextFieldPreview() {
    AppTheme {
        AuthTextField("Email", "", {}, true)
    }
}
package com.example.books.presentation.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.books.ui.theme.DarkBlue

@Composable
fun RoundedCornerOutlinedTextField(
    maxLine: Int = 1,
    singleLine: Boolean = true,
    value: String,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(0.8f),
        value = value,
        onValueChange = {
            onValueChanged(it)
        },
        isError = isError,
        label = { Text(text = label) },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(25.dp),
        colors = OutlinedTextFieldDefaults.colors().copy(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.White.copy(alpha = 0.8f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
            focusedTextColor = DarkBlue.copy(alpha = 0.8f),
            unfocusedTextColor = DarkBlue
        ),
        singleLine = singleLine,
        maxLines = maxLine
    )
}


@Composable
fun RoundedCornerButton(
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(0.5f),
        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = color
        )
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}
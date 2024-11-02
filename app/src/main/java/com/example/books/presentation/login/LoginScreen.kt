package com.example.books.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.books.R
import com.example.books.ui.theme.FilterColor

@Composable
fun LoginScreen(innerPadding: PaddingValues) {
    val viewModel: LoginViewModel = viewModel()
    val passwordVisible = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(R.drawable.background),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(FilterColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        RoundedCornerOutlinedTextField(
            value = viewModel.email.value,
            isError = !viewModel.emailCorrect.value,
            label = "Email",
            keyboardType = KeyboardType.Email,
            onValueChanged = {
                viewModel.email.value = it
                viewModel.validateEmail()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        RoundedCornerOutlinedTextField(
            value = viewModel.password.value,
            isError = !viewModel.passwordCorrect.value,
            label = "Password",
            keyboardType = KeyboardType.Password,
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChanged = {
                viewModel.password.value = it
                viewModel.validatePassword()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        RoundedCornerOutlinedTextField(
            value = viewModel.confirmPassword.value,
            isError = !viewModel.confirmPasswordCorrect.value,
            label = "Confirm password",
            keyboardType = KeyboardType.Password,
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChanged = {
                viewModel.confirmPassword.value = it
                viewModel.validateConfirmPassword()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Checkbox(
                checked = passwordVisible.value,
                onCheckedChange = { passwordVisible.value = it }
            )
            Text(
                text = "Show password",
                modifier = Modifier.clickable { passwordVisible.value = !passwordVisible.value })
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (!viewModel.emailCorrect.value) {
            Text(
                text = "The email was entered incorrectly",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(0.8f)
            )
        }
        if (!viewModel.passwordCorrect.value) {
            Text(
                text = "The password must contain at least 6 characters",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(0.8f)
            )
        }
        if (!viewModel.confirmPasswordCorrect.value) {
            Text(
                text = "Passwords do not match",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(0.8f)
            )
        }

        if (viewModel.errorMessage.value.isNotEmpty()) {
            Text(
                text = viewModel.errorMessage.value,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(0.8f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LoginButton("Sign up") {
            viewModel.createAccount()
        }

    }
}

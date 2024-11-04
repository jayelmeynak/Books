package com.example.books.presentation.addBook

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.books.R
import com.example.books.presentation.login.RoundedCornerButton
import com.example.books.presentation.login.RoundedCornerOutlinedTextField
import com.example.books.ui.theme.FilterColor


@Composable
fun AddBookScreen(
//    navController: NavController,
//    navigateToItem: (Screen) -> Unit
    onSaved: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val viewModel: AddBookViewModel = viewModel()

    val imageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { it ->
            viewModel.setImageUri(it)
        }
    Scaffold(
//        bottomBar = {
//            BottomMenu(navController) { item ->
//                navigateToItem(item)
//            }
//        }
    ) { innerPadding ->

        Box(
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
                }
        ) {

            Image(
                painter = rememberAsyncImagePainter(viewModel.selectImageUri.value),
                contentDescription = "Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.5f
            )


            Column(
                modifier = Modifier.fillMaxSize().padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.books),
                    contentDescription = "Logo",
                    modifier = Modifier.size(90.dp).clip(RoundedCornerShape(15.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = "Add Book",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                RoundedCornerDropDownMenu { category ->
                    viewModel.setCategory(category)
                }

                Spacer(modifier = Modifier.height(8.dp))

                RoundedCornerOutlinedTextField(
                    value = viewModel.bookTitle.value,
                    label = "Book title",
                    onValueChanged = { viewModel.setBookTitle(it) },
                    isError = false
                )

                Spacer(modifier = Modifier.height(8.dp))

                RoundedCornerOutlinedTextField(
                    maxLine = 5,
                    value = viewModel.bookDescription.value,
                    label = "Book description",
                    onValueChanged = { viewModel.setBookDescription(it) },
                    isError = false
                )

                Spacer(modifier = Modifier.height(8.dp))

                RoundedCornerOutlinedTextField(
                    singleLine = false,
                    value = viewModel.bookPrice.value,
                    label = "Book price",
                    onValueChanged = { viewModel.setBookPrice(it) },
                    isError = false,
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (viewModel.errorMessage.value.isNotEmpty()) {
                    Text(
                        text = viewModel.errorMessage.value,
                        color = Color.Red
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                RoundedCornerButton(
                    text = "Select image",
                    onClick = {
                        imageLauncher.launch("image/*")
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                RoundedCornerButton(
                    text = "Save",
                    onClick = {
                        viewModel.saveImageUri {
                            onSaved()
                        }
                    }
                )
                if (viewModel.loadingState.value) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onBackground,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Loading...",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

            }
        }
    }
}
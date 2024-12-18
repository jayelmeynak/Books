package com.example.books.presentation.addBook

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.books.ui.theme.DarkBlue

@Composable
fun RoundedCornerDropDownMenu(
    onCategorySelected: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val viewModel: AddEditBookViewModel = viewModel()
    val categoryList = listOf(
        "Fantasy",
        "Drama",
        "Bestsellers"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(25.dp)
            )
            .clip(RoundedCornerShape(25.dp))
            .clickable {
                expanded.value = !expanded.value
            }
            .background(Color.White.copy(alpha = 0.8f))

    ) {

        Text(
            text = viewModel.selectedCategory.value,
            color = DarkBlue,
            modifier = Modifier
                .padding(16.dp)
        )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            }) {
            categoryList.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(text = category)
                    },
                    onClick = {
                        expanded.value = false
                        onCategorySelected(category)
                    }
                )
            }
        }
    }
}
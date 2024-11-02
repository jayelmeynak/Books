package com.example.books.presentation.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.books.R
import com.example.books.ui.theme.DarkBlue
import com.example.books.ui.theme.GrayLight

@Composable
fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .background(DarkBlue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(id = R.drawable.books),
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(90.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Books Store",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

    }
}


@Composable
fun DrawerBody() {
    val categoryList = listOf(
        "All",
        "Favorites",
        "History",
        "Science",
        "Fiction",
        "Biography"
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.7f
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Categories",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(GrayLight)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(categoryList) { category ->
                    Column(modifier = Modifier.clickable {

                    }) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = category,
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(GrayLight)
                        )
                    }
                }
            }
        }
    }
}
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.books.R
import com.example.books.domain.Book
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

//        Spacer(modifier = Modifier.height(40.dp))

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
fun DrawerBody(
    onAdminClick: () -> Unit
) {
    val categoryList = listOf(
        "All",
        "Fantasy",
        "Drama",
        "Bestsellers"
    )
    val isAdminState = remember {
        mutableStateOf(false)
    }

    val viewModel: MainViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.isAdmin { isAdmin ->
            isAdminState.value = isAdmin
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
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

            if (isAdminState.value) {
                Button(
                    onClick = {
                        onAdminClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = "Admin panel",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                }
            }
        }
    }
}


@Composable
fun BookListItemUi(book: Book) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(15.dp)),
        elevation = CardDefaults.cardElevation(5.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(15.dp)),
                model = book.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = book.title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = book.description,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "${book.price}$",
                color = Color.Blue,
                fontSize = 14.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
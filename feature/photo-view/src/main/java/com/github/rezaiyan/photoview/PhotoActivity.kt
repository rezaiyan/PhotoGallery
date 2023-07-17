package com.github.rezaiyan.photoview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

@Composable
fun PhotoView(navController: NavHostController) {

    val viewModel = hiltViewModel<PhotoViewModel>()
    val uiState by viewModel.photoUiState.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            FullScreenImage(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.Black),
                imageUrl = uiState.url,
                title = uiState.title,
                onCloseClicked = {
                    navController.navigateUp()
                },
            )
        }
    )
}

@Composable
fun FullScreenImage(
    modifier: Modifier,
    imageUrl: String,
    title: String,
    onCloseClicked: () -> Unit
) {
    Box(modifier = modifier) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = "Full screen image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
        )
        IconButton(
            onClick = onCloseClicked,
            modifier = Modifier
                .padding(top = 24.dp, end = 24.dp)
                .size(52.dp)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Close",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


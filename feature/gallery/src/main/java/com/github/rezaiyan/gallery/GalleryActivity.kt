@file:OptIn(ExperimentalMaterialApi::class)

package com.github.rezaiyan.gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.github.rezaiyan.compose.theme.PhotoGalleryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoGalleryTheme {

                val viewModel = viewModel<GalleryViewModel>()
                val uiState by viewModel.galleryUiState.collectAsState()
                val pullRefreshState = rememberPullRefreshState(uiState.loading, { viewModel.refresh() })
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            SearchTextField(value = uiState.inputText, onValueChange = viewModel::search)
                        }
                    },
                    content = { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .pullRefresh(pullRefreshState)
                                .padding(paddingValues)
                        ) {
                            ImageGridList(imageUrls = uiState.photos)
                            PullRefreshIndicator(
                                uiState.loading, pullRefreshState,
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                    }
                )
            }
        }
    }

}

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        textColor = Color.Black,
        cursorColor = Color.Gray,
        leadingIconColor = Color.Gray,
        trailingIconColor = Color.Gray,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Search") },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White),
        textStyle = TextStyle(fontSize = 16.sp),
        colors = textFieldColors,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onValueChange(value) }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear Icon",
                        tint = Color.Gray
                    )
                }
            }
        },
        singleLine = true,
        maxLines = 1
    )
}


@Composable
fun ImageGridList(imageUrls: List<String>) {
    val columnWidth = 120.dp

    LazyVerticalGrid(
        columns = GridCells.Adaptive(columnWidth),
    ) {
        items(items = imageUrls) { imageUrl ->
            ImageItem(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp),
                imageUrl = imageUrl,
            )
        }
    }
}

@Composable
fun ImageItem(modifier: Modifier, imageUrl: String) {
    val painter = rememberAsyncImagePainter(model = imageUrl)

    Box(modifier = modifier) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhotoGalleryTheme {
        ImageGridList(listOf("", "", ""))
    }
}
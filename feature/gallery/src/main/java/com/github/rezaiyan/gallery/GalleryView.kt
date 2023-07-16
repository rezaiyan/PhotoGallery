@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package com.github.rezaiyan.gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.rezaiyan.compose.theme.PhotoGalleryTheme
import com.github.rezaiyan.domain.model.Photo
import com.github.rezaiyan.gallery.ui.ImageGridList
import com.github.rezaiyan.gallery.ui.SearchTextField


@Composable
fun GalleryView(onPhotoClick: (Photo) -> Unit) {

    val viewModel = hiltViewModel<GalleryViewModel>()
    val uiState by viewModel.galleryUiState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(uiState.loading, { viewModel.refresh() })
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                value = uiState.inputText,
                onValueChange = viewModel::search,
            )
        },
        content = { paddingValues ->
            GalleryContainer(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
                    .padding(paddingValues),
                pullRefreshState = pullRefreshState,
                uiState = uiState,
                onPhotoClick = onPhotoClick
            )
        }
    )
}

@Composable
private fun GalleryContainer(
    modifier: Modifier,
    pullRefreshState: PullRefreshState,
    uiState: GalleryViewModel.GalleryUiState,
    onPhotoClick: (Photo) -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        ImageGridList(photos = uiState.photos, onPhotoClick = onPhotoClick)
        PullRefreshIndicator(
            uiState.loading, pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhotoGalleryTheme {
        ImageGridList(emptyList()) {}
    }
}
package com.github.rezaiyan.gallery

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.rezaiyan.domain.model.Photo
import com.github.rezaiyan.domain.repo.FlickrRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GalleryViewModel @Inject constructor(
    private val repo: FlickrRepo,
) : ViewModel() {

    private val _galleryUiState = MutableStateFlow(GalleryUiState())
    val galleryUiState: StateFlow<GalleryUiState> = _galleryUiState.asStateFlow()

    fun refresh() {
        loadPhotos(_galleryUiState.value.inputText)
    }

    // show the loading only if it's the first time or user cleared the input field
    private fun loadPhotos(tags: String = "") {
        _galleryUiState.update {
            val loading = tags.isEmpty()
            it.copy(loading = loading)
        }
        viewModelScope.launch {
            repo.getPhotos(tags).collect { response ->
                _galleryUiState.update {
                    it.copy(photos = response, loading = false)
                }
            }
        }
    }

    fun search(tags: String) {
        viewModelScope.launch {
            _galleryUiState.update { it.copy(inputText = tags) }
            loadPhotos(tags = tags)
        }
    }

    @Immutable
    data class GalleryUiState(
        val inputText: String = "",
        val tags: List<String> = emptyList(),
        val photos: List<Photo> = emptyList(),
        val loading: Boolean = false
    )

}
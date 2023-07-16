package com.github.rezaiyan.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.rezaiyan.domain.repo.FlickrRepo
import com.github.rezaiyan.domain.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.update

@HiltViewModel
internal open class GalleryViewModel @Inject constructor(private val repo: FlickrRepo) : ViewModel() {

    private val _galleryUiState = MutableStateFlow(GalleryUiState())
    val galleryUiState: StateFlow<GalleryUiState> = _galleryUiState.asStateFlow()

    init {
        loadPhotos()
    }

    fun refresh() {
        loadPhotos(_galleryUiState.value.inputText)
    }

    private fun loadPhotos(tags: String = "") {
        _galleryUiState.update { it.copy(loading = tags.isEmpty()) }
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
            delay(350)
            loadPhotos(tags = tags)
        }
    }

    data class GalleryUiState(
        val inputText: String = "",
        val tags: List<String> = emptyList(),
        val photos: List<Photo> = emptyList(),
        val loading: Boolean = false
    )

}
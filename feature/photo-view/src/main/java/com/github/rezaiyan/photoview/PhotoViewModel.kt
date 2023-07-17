package com.github.rezaiyan.photoview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.rezaiyan.domain.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class PhotoViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val url: String = checkNotNull(savedStateHandle["url"])
    private val title: String = checkNotNull(savedStateHandle["title"])

    private val _photoUiState = MutableStateFlow(Photo("", ""))
    val photoUiState: StateFlow<Photo> = _photoUiState.asStateFlow()

    init {
        _photoUiState.update { Photo(url = url, title = title) }
    }
}
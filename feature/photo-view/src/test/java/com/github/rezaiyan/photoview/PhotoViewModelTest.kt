package com.github.rezaiyan.photoview

import androidx.lifecycle.SavedStateHandle
import com.github.rezaiyan.domain.model.Photo
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class PhotoViewModelTest {

    private lateinit var photoViewModel: PhotoViewModel
    private val mockSavedStateHandle: SavedStateHandle = mockk()

    @After
    fun cleanup() {
        clearMocks(mockSavedStateHandle)
    }

    @Test
    fun `photoUiState emits correct initial value`() = runBlockingTest {
        val url = "https://example.com/photo.jpg"
        val title = "Example Photo"
        every { checkNotNull(mockSavedStateHandle["url"]) } returns url
        every { checkNotNull(mockSavedStateHandle["title"]) } returns title
        photoViewModel = PhotoViewModel(mockSavedStateHandle)

        var photo: Photo? = null
        val job = launch {
            photoViewModel.photoUiState.collect { value ->
                photo = value
            }
        }

        assertEquals(url, photo?.url)
        assertEquals(title, photo?.title)

        job.cancel()
    }
}

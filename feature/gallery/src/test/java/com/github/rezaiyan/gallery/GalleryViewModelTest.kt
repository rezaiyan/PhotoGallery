package com.github.rezaiyan.gallery

import com.github.rezaiyan.domain.model.Photo
import com.github.rezaiyan.domain.repo.FlickrRepo
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GalleryViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var galleryViewModel: GalleryViewModel
    private val mockRepo: FlickrRepo = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        galleryViewModel = GalleryViewModel(mockRepo)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        clearMocks(mockRepo)
    }

    @Test
    fun `loadPhotos sets correct loading state and updates photos`() = runTest {
        val emptyTag = ""
        val photos = listOf(
            Photo("https://example.com/photo1.jpg", "Photo 1"),
            Photo("https://example.com/photo2.jpg", "Photo 2")
        )
        coEvery { mockRepo.getPhotos(emptyTag) } returns flowOf(photos)

        galleryViewModel.refresh()

        val updatedUiState = galleryViewModel.galleryUiState.first()
        assertEquals(false, updatedUiState.loading)
        assertEquals(photos, updatedUiState.photos)

        coVerify { mockRepo.getPhotos(emptyTag) }
    }

    @Test
    fun `search updates inputText and triggers loadPhotos`() = runTest {
        val tags = "nature"

        coEvery { mockRepo.getPhotos(tags) } returns flowOf(emptyList())
        galleryViewModel.search(tags)

        val uiState = galleryViewModel.galleryUiState.first()
        assertEquals(tags, uiState.inputText)

        coVerify { mockRepo.getPhotos(tags) }
    }

    @Test
    fun `refresh triggers loadPhotos with current inputText`() = runTest {
        val inputText = "flowers"
        coEvery { mockRepo.getPhotos(inputText) } returns flowOf(emptyList())

        galleryViewModel.search(inputText)
        galleryViewModel.refresh()

        coVerify { mockRepo.getPhotos(inputText) }
    }
}


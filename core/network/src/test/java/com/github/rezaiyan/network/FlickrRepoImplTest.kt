package com.github.rezaiyan.network

import com.github.rezaiyan.domain.model.ImageMedia
import com.github.rezaiyan.domain.model.PhotoItem
import com.github.rezaiyan.domain.model.PublicPhotosResponse
import com.github.rezaiyan.network.repo.FlickrRepoImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class FlickrRepoImplTest {

    private val mockFlickrService: FlickrService = mockk()
    private val flickrRepo = FlickrRepoImpl(mockFlickrService)

    @Test
    fun `getPhotos returns correct photos`() = runBlocking {
        val tags = "cats"
        val title = "Example Photo"
        val url = "https://example.com/photo.jpg"
        val mockPhotoItem = PhotoItem(
            tags = "nature",
            author = "John Doe",
            description = "Example description",
            published = "2023-01-01T00:00:00Z",
            title = "Example Photo",
            media = ImageMedia(url)
        )
        val mockPublicPhotosResponse = PublicPhotosResponse(
            title = "Example Title",
            items = listOf(mockPhotoItem)
        )
        coEvery { mockFlickrService.getPhotos(tags) } returns mockPublicPhotosResponse

        val photos = flickrRepo.getPhotos(tags).single()

        assertEquals(1, photos.size)
        assertEquals(url, photos[0].url)
        assertEquals(title, photos[0].title)
    }
}

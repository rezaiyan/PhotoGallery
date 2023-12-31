package com.github.rezaiyan.network.repo

import com.github.rezaiyan.domain.model.Photo
import com.github.rezaiyan.domain.model.PhotoItem
import com.github.rezaiyan.domain.repo.FlickrRepo
import com.github.rezaiyan.network.FlickrService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FlickrRepoImpl @Inject constructor(
    private val flickrService: FlickrService,
) : FlickrRepo {

    override suspend fun getPhotos(tags: String) = flow {
        val response = flickrService.getPhotos(tags).items.map {
            Photo(url = it.media.m, title = getTitle(it))
        }
        emit(response)
    }.flowOn(Dispatchers.IO)

    private fun getTitle(it: PhotoItem) = it.title.trim().ifEmpty { it.media.m.fileName() }

    private fun String.fileName(): String {
        return substringAfterLast("/")
            .substringBeforeLast(".")
    }

}
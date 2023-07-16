package com.github.rezaiyan.domain.repo

import com.github.rezaiyan.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface FlickrRepo {
    suspend fun getPhotos(tags: String): Flow<List<Photo>>
}
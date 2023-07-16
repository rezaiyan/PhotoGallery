package com.github.rezaiyan.domain.repo

import kotlinx.coroutines.flow.Flow

interface FlickrRepo {
    suspend fun getPhotos(tags: String): Flow<List<String>>
}
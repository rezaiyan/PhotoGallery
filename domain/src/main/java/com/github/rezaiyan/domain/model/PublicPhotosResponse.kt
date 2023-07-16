package com.github.rezaiyan.domain.model

data class PublicPhotosResponse(
    val title: String,
    val items: List<PhotoItem>,
)

data class PhotoItem(
    val title: String,
    val tags: String,
    val author: String,
    val description: String,
    val published: String,
    val media: ImageMedia,
)

data class ImageMedia(
    val m: String,
)
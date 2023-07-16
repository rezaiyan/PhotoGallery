package com.github.rezaiyan.network

import com.github.rezaiyan.domain.model.PublicPhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {

    @GET("feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getPhotos(@Query("tags") tags: String): PublicPhotosResponse

}
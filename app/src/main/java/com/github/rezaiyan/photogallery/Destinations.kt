package com.github.rezaiyan.photogallery

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.github.rezaiyan.domain.model.Photo

sealed class Destinations {
    abstract val route: String

    object Gallery : Destinations() {
        override val route: String
            get() = "gallery"
    }

    object PhotoView : Destinations() {
        fun createRoute(photo: Photo) =
            "photo/" +
                Uri.encode(photo.url) +
                "/" +
                Uri.encode(photo.title)

        val arguments = listOf(
            navArgument("url") {
                type = NavType.StringType
            },
            navArgument("title") {
                type = NavType.StringType
            }
        )
        override val route: String
            get() = "photo/{url}/{title}"
    }

}
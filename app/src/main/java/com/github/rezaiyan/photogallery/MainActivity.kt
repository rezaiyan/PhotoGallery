@file:OptIn(ExperimentalAnimationApi::class)

package com.github.rezaiyan.photogallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.rezaiyan.compose.theme.PhotoGalleryTheme
import com.github.rezaiyan.gallery.GalleryView
import com.github.rezaiyan.photoview.PhotoView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoGalleryTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Destinations.Gallery.route
                ) {
                    galleryViewComposable(navController)
                    photoViewComposable(navController)
                }
            }
        }
    }

    private fun NavGraphBuilder.galleryViewComposable(navController: NavHostController) {
        composable(
            route = Destinations.Gallery.route
        ) {
            GalleryView { photo ->
                navController.navigate(Destinations.PhotoView.createRoute(photo))
            }
        }
    }

    private fun NavGraphBuilder.photoViewComposable(navController: NavHostController) {
        composable(
            route = Destinations.PhotoView.route,
            arguments = Destinations.PhotoView.arguments
        ) {
            PhotoView(navController)
        }
    }

}
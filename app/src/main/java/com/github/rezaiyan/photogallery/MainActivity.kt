package com.github.rezaiyan.photogallery

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

                NavHost(navController = navController, startDestination = "gallery") {
                    composable(
                        route = "gallery"
                    ) {
                        GalleryView { photo ->
                            navController.navigate(
                                route = "photo/" +
                                    Uri.encode(photo.url) +
                                    "/" +
                                    Uri.encode(photo.title)
                            )
                        }
                    }
                    composable(
                        route = "photo/{url}/{title}",
                        arguments = listOf(navArgument("url") {
                            type = NavType.StringType
                        }, navArgument("title") {
                            type = NavType.StringType
                        })
                    ) {
                        PhotoView(navController)
                    }
                }
            }
        }
    }

}
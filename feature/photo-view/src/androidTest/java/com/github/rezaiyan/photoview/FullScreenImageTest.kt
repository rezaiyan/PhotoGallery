package com.github.rezaiyan.photoview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class FullScreenImageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun fullScreenImageRendersCorrectly() {
        val imageUrl = "https://example.com/image.jpg"
        val title = "Example Image"

        composeTestRule.setContent {
            FullScreenImage(
                modifier = Modifier.fillMaxSize(),
                imageUrl = imageUrl,
                title = title,
                onCloseClicked = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Full screen image").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Close").assertIsDisplayed()
        composeTestRule.onNodeWithText(title).assertIsDisplayed()
    }

}

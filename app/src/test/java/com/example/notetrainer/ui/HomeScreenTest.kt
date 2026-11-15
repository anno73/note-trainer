package com.example.notetrainer.ui

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.example.notetrainer.R
import com.example.notetrainer.ui.theme.ThemeMode
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], qualifiers = "w1000dp-h800dp")
class HomeScreenTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun titleIsDisplayedAndContentWidthIsCapped() {
        compose.setContent {
            MaterialTheme {
                HomeScreen(themeMode = ThemeMode.SYSTEM, onThemeModeChange = {})
            }
        }
        val context = ApplicationProvider.getApplicationContext<Context>()
        compose.onNodeWithText(context.getString(R.string.app_name)).assertIsDisplayed()
        val widthPx = compose.onNodeWithTag("home_content").fetchSemanticsNode().boundsInRoot.width
        assertTrue("Content exceeds 600 dp", widthPx <= 600 * compose.density.density + 1)
        assertTrue("Test screen must be wider than 600 dp", context.resources.configuration.screenWidthDp > 600)
    }

    @Test
    fun selectingThemeNotifiesCaller() {
        var selected = ThemeMode.SYSTEM
        compose.setContent {
            MaterialTheme {
                HomeScreen(themeMode = ThemeMode.SYSTEM, onThemeModeChange = { selected = it })
            }
        }
        val context = ApplicationProvider.getApplicationContext<Context>()
        compose.onNodeWithText(context.getString(R.string.theme_system)).assertIsSelected()
        compose.onNodeWithText(context.getString(R.string.theme_dark)).performClick()
        compose.runOnIdle { assertTrue(selected == ThemeMode.DARK) }
    }
}

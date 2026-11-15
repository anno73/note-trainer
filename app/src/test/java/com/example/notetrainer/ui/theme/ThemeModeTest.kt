package com.example.notetrainer.ui.theme

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThemeModeTest {
    @Test
    fun explicitModesIgnoreSystemTheme() {
        assertFalse(ThemeMode.WHITE.isDark(false))
        assertFalse(ThemeMode.WHITE.isDark(true))
        assertTrue(ThemeMode.DARK.isDark(false))
        assertTrue(ThemeMode.DARK.isDark(true))
    }

    @Test
    fun systemModeFollowsSystemTheme() {
        assertFalse(ThemeMode.SYSTEM.isDark(false))
        assertTrue(ThemeMode.SYSTEM.isDark(true))
    }
}

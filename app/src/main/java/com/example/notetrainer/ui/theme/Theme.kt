package com.example.notetrainer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

enum class ThemeMode {
    WHITE,
    DARK,
    SYSTEM,
}

fun ThemeMode.isDark(systemIsDark: Boolean): Boolean = when (this) {
    ThemeMode.WHITE -> false
    ThemeMode.DARK -> true
    ThemeMode.SYSTEM -> systemIsDark
}

@Composable
fun NoteTrainerTheme(themeMode: ThemeMode, content: @Composable () -> Unit) {
    val dark = themeMode.isDark(isSystemInDarkTheme())
    MaterialTheme(
        colorScheme = if (dark) darkColorScheme() else lightColorScheme(),
        content = content,
    )
}

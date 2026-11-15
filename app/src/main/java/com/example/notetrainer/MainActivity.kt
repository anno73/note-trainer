package com.example.notetrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.notetrainer.ui.HomeScreen
import com.example.notetrainer.ui.theme.NoteTrainerTheme
import com.example.notetrainer.ui.theme.ThemeMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var themeMode by rememberSaveable { mutableStateOf(ThemeMode.SYSTEM) }
            NoteTrainerTheme(themeMode) {
                HomeScreen(themeMode = themeMode, onThemeModeChange = { themeMode = it })
            }
        }
    }
}

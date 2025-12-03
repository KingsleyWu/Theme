package com.just.theme.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.just.theme.ui.screens.theme.ThemeEditorScreen
import com.just.theme.ui.screens.theme.ThemePaletteScreen
import com.just.theme.ui.screens.theme.ThemePreviewScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeScreen(
    isDark: Boolean,
    onToggleDark: () -> Unit,
    onPickSeed: () -> Unit,
    currentScheme: ColorScheme,
    onSchemeChange: (ColorScheme) -> Unit,
    onResetScheme: () -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Palette", "Preview", "Editor")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Theme Studio") },
                actions = {
                    IconButton(onClick = { onPickSeed() }) {
                        Icon(Icons.Default.ColorLens, contentDescription = "Dynamic Color")
                    }
                    IconButton(onClick = onToggleDark) {
                        Icon(
                            if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Dark Mode"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            SecondaryTabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> ThemePaletteScreen()
                1 -> ThemePreviewScreen()
                2 -> ThemeEditorScreen(
                    scheme = currentScheme,
                    onSchemeChange = onSchemeChange,
                    onReset = onResetScheme
                )
            }
        }
    }
}

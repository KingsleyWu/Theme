package com.just.theme

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import com.just.theme.ui.components.AppNavigation
import com.just.theme.ui.components.ColorPickerDialog
import com.just.theme.ui.navigation.Screen
import com.just.theme.ui.responsive.LocalAppLayoutConfig
import com.just.theme.ui.responsive.rememberAppLayoutConfig
import com.just.theme.ui.screens.AnalysisScreen
import com.just.theme.ui.screens.HomeScreen
import com.just.theme.ui.screens.SettingsScreen
import com.just.theme.ui.screens.ThemeScreen
import com.materialkolor.dynamicColorScheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * 应用入口点。
 * 负责管理全局状态（主题、导航）以及整体布局结构。
 */
@Composable
@Preview
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun App() {
    var isDark by remember { mutableStateOf(false) }
    
    var lightScheme by remember { mutableStateOf(lightColorScheme()) }
    var darkScheme by remember { mutableStateOf(darkColorScheme()) }
    
    // 动态取色相关状态
    var showSeedPicker by remember { mutableStateOf(false) }
    
    // 导航状态
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    if (showSeedPicker) {
        ColorPickerDialog(
            initialColor = lightScheme.primary,
            onConfirm = { seedColor ->
                lightScheme = dynamicColorScheme(seedColor, isDark = false)
                darkScheme = dynamicColorScheme(seedColor, isDark = true)
                showSeedPicker = false
            },
            onDismiss = { showSeedPicker = false }
        )
    }

    val currentScheme = if (isDark) darkScheme else lightScheme
    
    MaterialTheme(colorScheme = currentScheme) {
        BoxWithConstraints {
            val windowSize = WindowSizeClass.calculateFromSize(DpSize(maxWidth, maxHeight))
            val layoutConfig = rememberAppLayoutConfig(windowSize)
            
            CompositionLocalProvider(LocalAppLayoutConfig provides layoutConfig) {
                AppNavigation(
                    currentScreen = currentScreen,
                    onNavigate = { currentScreen = it }
                ) {
                    // 使用 Crossfade 进行简单的转场动画
                    Crossfade(
                        targetState = currentScreen
                    ) { screen ->
                        when (screen) {
                            Screen.Home -> HomeScreen(
                                onNavigateToTheme = { currentScreen = Screen.Theme },
                                onNavigateToAnalysis = { currentScreen = Screen.Analysis }
                            )
                            Screen.Theme -> ThemeScreen(
                                isDark = isDark,
                                onToggleDark = { isDark = !isDark },
                                onPickSeed = { showSeedPicker = true },
                                currentScheme = currentScheme,
                                onSchemeChange = { newScheme ->
                                    if (isDark) darkScheme = newScheme else lightScheme = newScheme
                                },
                                onResetScheme = {
                                    if (isDark) darkScheme = darkColorScheme() else lightScheme = lightColorScheme()
                                }
                            )
                            Screen.Analysis -> AnalysisScreen()
                            Screen.Settings -> SettingsScreen()
                        }
                    }
                }
            }
        }
    }
}

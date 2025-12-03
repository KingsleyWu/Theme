package com.just.theme.ui.responsive

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

enum class AppContentType {
    SINGLE_PANE, DUAL_PANE
}

enum class AppNavigationType {
    BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}

data class AppLayoutConfig(
    val navigationType: AppNavigationType,
    val contentType: AppContentType
)

val LocalAppLayoutConfig = compositionLocalOf<AppLayoutConfig> {
    error("No AppLayoutConfig provided")
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun rememberAppLayoutConfig(windowSize: WindowSizeClass): AppLayoutConfig {
    val navigationType: AppNavigationType
    val contentType: AppContentType

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = AppNavigationType.BOTTOM_NAVIGATION
            contentType = AppContentType.SINGLE_PANE
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = AppNavigationType.NAVIGATION_RAIL
            contentType = AppContentType.SINGLE_PANE
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = if (windowSize.heightSizeClass == WindowHeightSizeClass.Compact) {
                AppNavigationType.NAVIGATION_RAIL
            } else {
                AppNavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = AppContentType.DUAL_PANE
        }
        else -> {
            navigationType = AppNavigationType.BOTTOM_NAVIGATION
            contentType = AppContentType.SINGLE_PANE
        }
    }
    
    return AppLayoutConfig(navigationType, contentType)
}

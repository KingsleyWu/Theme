package com.just.theme.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import com.just.theme.ui.navigation.Screen
import com.just.theme.ui.navigation.bottomNavItems
import com.just.theme.ui.responsive.AppNavigationType
import com.just.theme.ui.responsive.LocalAppLayoutConfig

@Composable
fun AppNavigation(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    content: @Composable () -> Unit
) {
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            bottomNavItems.forEach { screen ->
                item(
                    selected = currentScreen == screen,
                    onClick = { onNavigate(screen) },
                    icon = { Icon(screen.icon, contentDescription = screen.title) },
                    label = { Text(screen.title) }
                )
            }
        }
    ) {
        content()
    }
}

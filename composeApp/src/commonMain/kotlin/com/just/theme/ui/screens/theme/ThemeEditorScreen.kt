package com.just.theme.ui.screens.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.just.theme.ui.SectionTitle
import com.just.theme.ui.components.ColorField
import com.just.theme.ui.components.ExportCodeDialog
import com.just.theme.util.colorToHex

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ThemeEditorScreen(
    scheme: ColorScheme,
    onSchemeChange: (ColorScheme) -> Unit,
    onReset: () -> Unit
) {
    val s = remember(scheme) { editorStateFromScheme(scheme) }
    var state by remember { mutableStateOf(s) }
    var showExportDialog by remember { mutableStateOf(false) }

    if (showExportDialog) {
        ExportCodeDialog(scheme = buildScheme(state), onDismiss = { showExportDialog = false })
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showExportDialog = true }) {
                Icon(Icons.Default.Share, contentDescription = "Export Code")
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Custom Theme Editor",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = onReset) { Text("Reset to Default") }
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) { SectionTitle("Primary") }
            item { ColorField("primary", state.primary) { state = state.copy(primary = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onPrimary", state.onPrimary) { state = state.copy(onPrimary = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("primaryContainer", state.primaryContainer) { state = state.copy(primaryContainer = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onPrimaryContainer", state.onPrimaryContainer) { state = state.copy(onPrimaryContainer = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("inversePrimary", state.inversePrimary) { state = state.copy(inversePrimary = it); onSchemeChange(buildScheme(state)) } }

            item(span = { GridItemSpan(maxLineSpan) }) { SectionTitle("Secondary") }
            item { ColorField("secondary", state.secondary) { state = state.copy(secondary = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onSecondary", state.onSecondary) { state = state.copy(onSecondary = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("secondaryContainer", state.secondaryContainer) { state = state.copy(secondaryContainer = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onSecondaryContainer", state.onSecondaryContainer) { state = state.copy(onSecondaryContainer = it); onSchemeChange(buildScheme(state)) } }

            item(span = { GridItemSpan(maxLineSpan) }) { SectionTitle("Tertiary") }
            item { ColorField("tertiary", state.tertiary) { state = state.copy(tertiary = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onTertiary", state.onTertiary) { state = state.copy(onTertiary = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("tertiaryContainer", state.tertiaryContainer) { state = state.copy(tertiaryContainer = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onTertiaryContainer", state.onTertiaryContainer) { state = state.copy(onTertiaryContainer = it); onSchemeChange(buildScheme(state)) } }

            item(span = { GridItemSpan(maxLineSpan) }) { SectionTitle("Surface/Background") }
            item { ColorField("background", state.background) { state = state.copy(background = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onBackground", state.onBackground) { state = state.copy(onBackground = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("surface", state.surface) { state = state.copy(surface = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onSurface", state.onSurface) { state = state.copy(onSurface = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("surfaceVariant", state.surfaceVariant) { state = state.copy(surfaceVariant = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onSurfaceVariant", state.onSurfaceVariant) { state = state.copy(onSurfaceVariant = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("surfaceTint", state.surfaceTint) { state = state.copy(surfaceTint = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("inverseSurface", state.inverseSurface) { state = state.copy(inverseSurface = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("inverseOnSurface", state.inverseOnSurface) { state = state.copy(inverseOnSurface = it); onSchemeChange(buildScheme(state)) } }

            item(span = { GridItemSpan(maxLineSpan) }) { SectionTitle("Error/Outline/Scrim") }
            item { ColorField("error", state.error) { state = state.copy(error = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onError", state.onError) { state = state.copy(onError = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("errorContainer", state.errorContainer) { state = state.copy(errorContainer = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onErrorContainer", state.onErrorContainer) { state = state.copy(onErrorContainer = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("outline", state.outline) { state = state.copy(outline = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("outlineVariant", state.outlineVariant) { state = state.copy(outlineVariant = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("scrim", state.scrim) { state = state.copy(scrim = it); onSchemeChange(buildScheme(state)) } }

            item(span = { GridItemSpan(maxLineSpan) }) { SectionTitle("Surface Containers") }
            item { ColorField("surfaceBright", state.surfaceBright) { state = state.copy(surfaceBright = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("surfaceDim", state.surfaceDim) { state = state.copy(surfaceDim = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("surfaceContainerLowest", state.surfaceContainerLowest) { state = state.copy(surfaceContainerLowest = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("surfaceContainerLow", state.surfaceContainerLow) { state = state.copy(surfaceContainerLow = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("surfaceContainer", state.surfaceContainer) { state = state.copy(surfaceContainer = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("surfaceContainerHigh", state.surfaceContainerHigh) { state = state.copy(surfaceContainerHigh = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("surfaceContainerHighest", state.surfaceContainerHighest) { state = state.copy(surfaceContainerHighest = it); onSchemeChange(buildScheme(state)) } }

            item(span = { GridItemSpan(maxLineSpan) }) { SectionTitle("Fixed Colors") }
            item { ColorField("primaryFixed", state.primaryFixed) { state = state.copy(primaryFixed = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("primaryFixedDim", state.primaryFixedDim) { state = state.copy(primaryFixedDim = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onPrimaryFixed", state.onPrimaryFixed) { state = state.copy(onPrimaryFixed = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onPrimaryFixedVariant", state.onPrimaryFixedVariant) { state = state.copy(onPrimaryFixedVariant = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("secondaryFixed", state.secondaryFixed) { state = state.copy(secondaryFixed = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("secondaryFixedDim", state.secondaryFixedDim) { state = state.copy(secondaryFixedDim = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onSecondaryFixed", state.onSecondaryFixed) { state = state.copy(onSecondaryFixed = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onSecondaryFixedVariant", state.onSecondaryFixedVariant) { state = state.copy(onSecondaryFixedVariant = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("tertiaryFixed", state.tertiaryFixed) { state = state.copy(tertiaryFixed = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("tertiaryFixedDim", state.tertiaryFixedDim) { state = state.copy(tertiaryFixedDim = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onTertiaryFixed", state.onTertiaryFixed) { state = state.copy(onTertiaryFixed = it); onSchemeChange(buildScheme(state)) } }
            item { ColorField("onTertiaryFixedVariant", state.onTertiaryFixedVariant) { state = state.copy(onTertiaryFixedVariant = it); onSchemeChange(buildScheme(state)) } }
        }
    }
}

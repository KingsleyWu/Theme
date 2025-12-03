package com.just.theme.ui.screens.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.just.theme.util.colorToHex
import com.just.theme.util.toClipEntry
import kotlinx.coroutines.launch

@Composable
fun ThemePaletteScreen() {
    val scheme = MaterialTheme.colorScheme

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            SectionHeader("Accent Colors (Key Colors)")
        }
        
        item { ColorCard("Primary", scheme.primary, scheme.onPrimary) }
        item { ColorCard("On Primary", scheme.onPrimary, scheme.primary) }
        item { ColorCard("Primary Container", scheme.primaryContainer, scheme.onPrimaryContainer) }
        item { ColorCard("On Primary Container", scheme.onPrimaryContainer, scheme.primaryContainer) }
        
        item { ColorCard("Secondary", scheme.secondary, scheme.onSecondary) }
        item { ColorCard("On Secondary", scheme.onSecondary, scheme.secondary) }
        item { ColorCard("Secondary Container", scheme.secondaryContainer, scheme.onSecondaryContainer) }
        item { ColorCard("On Secondary Container", scheme.onSecondaryContainer, scheme.secondaryContainer) }
        
        item { ColorCard("Tertiary", scheme.tertiary, scheme.onTertiary) }
        item { ColorCard("On Tertiary", scheme.onTertiary, scheme.tertiary) }
        item { ColorCard("Tertiary Container", scheme.tertiaryContainer, scheme.onTertiaryContainer) }
        item { ColorCard("On Tertiary Container", scheme.onTertiaryContainer, scheme.tertiaryContainer) }

        item(span = { GridItemSpan(maxLineSpan) }) {
            SectionHeader("Neutral Colors (Surfaces & Backgrounds)")
        }

        item { ColorCard("Background", scheme.background, scheme.onBackground) }
        item { ColorCard("On Background", scheme.onBackground, scheme.background) }
        item { ColorCard("Surface", scheme.surface, scheme.onSurface) }
        item { ColorCard("On Surface", scheme.onSurface, scheme.surface) }
        
        item { ColorCard("Surface Variant", scheme.surfaceVariant, scheme.onSurfaceVariant) }
        item { ColorCard("On Surface Variant", scheme.onSurfaceVariant, scheme.surfaceVariant) }
        item { ColorCard("Outline", scheme.outline, scheme.surface) }
        item { ColorCard("Outline Variant", scheme.outlineVariant, scheme.onSurfaceVariant) }

        item(span = { GridItemSpan(maxLineSpan) }) {
            SectionHeader("Functional Colors (Error)")
        }

        item { ColorCard("Error", scheme.error, scheme.onError) }
        item { ColorCard("On Error", scheme.onError, scheme.error) }
        item { ColorCard("Error Container", scheme.errorContainer, scheme.onErrorContainer) }
        item { ColorCard("On Error Container", scheme.onErrorContainer, scheme.errorContainer) }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun ColorCard(name: String, color: Color, contentColor: Color) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    val hex = colorToHex(color)
    
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = Modifier
            .height(100.dp)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .clickable {
                // Copy to clipboard
                scope.launch {
                    clipboard.setClipEntry(hex.toClipEntry())
                }
            }
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            Column(modifier = Modifier.align(Alignment.TopStart)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.labelMedium,
                    color = contentColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = hex,
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor.copy(alpha = 0.8f),
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
            
            // Icon or indicator that it's clickable/copyable?
        }
    }
}

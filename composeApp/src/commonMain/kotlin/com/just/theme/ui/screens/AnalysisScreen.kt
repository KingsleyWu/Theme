package com.just.theme.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.just.theme.util.contrastRatio
import kotlin.math.roundToInt

@Composable
fun AnalysisScreen() {
    val scheme = MaterialTheme.colorScheme
    
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                Text(
                    text = "主题颜色分析",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "对比度检查 (WCAG)",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        
        item { ContrastCard("Primary / OnPrimary", scheme.primary, scheme.onPrimary) }
        item { ContrastCard("PrimaryContainer / OnPrimaryContainer", scheme.primaryContainer, scheme.onPrimaryContainer) }
        item { ContrastCard("Secondary / OnSecondary", scheme.secondary, scheme.onSecondary) }
        item { ContrastCard("SecondaryContainer / OnSecondaryContainer", scheme.secondaryContainer, scheme.onSecondaryContainer) }
        item { ContrastCard("Tertiary / OnTertiary", scheme.tertiary, scheme.onTertiary) }
        item { ContrastCard("TertiaryContainer / OnTertiaryContainer", scheme.tertiaryContainer, scheme.onTertiaryContainer) }
        item { ContrastCard("Background / OnBackground", scheme.background, scheme.onBackground) }
        item { ContrastCard("Surface / OnSurface", scheme.surface, scheme.onSurface) }
        item { ContrastCard("Error / OnError", scheme.error, scheme.onError) }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContrastCard(label: String, bg: Color, fg: Color) {
    val ratio = contrastRatio(bg, fg)
    val ratioFormatted = (ratio * 100).roundToInt() / 100f
    
    val isAA = ratio >= 4.5
    val isAALarge = ratio >= 3.0
    val isAAA = ratio >= 7.0
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = bg, contentColor = fg)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Sample Text",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Column(horizontalAlignment = Alignment.End) {
                    Text("Ratio: $ratioFormatted:1", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Badge(label = "AA", pass = isAA)
                        Badge(label = "AA Large", pass = isAALarge)
                        Badge(label = "AAA", pass = isAAA)
                    }
                }
            }
        }
    }
}

@Composable
fun Badge(label: String, pass: Boolean) {
    Surface(
        color = if (pass) Color(0xFF4CAF50) else Color(0xFFFF5252),
        contentColor = Color.White,
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                imageVector = if (pass) Icons.Default.CheckCircle else Icons.Default.Warning,
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )
            Text(label, style = MaterialTheme.typography.labelSmall)
        }
    }
}

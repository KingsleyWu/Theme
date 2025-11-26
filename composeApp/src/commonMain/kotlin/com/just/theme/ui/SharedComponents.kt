package com.just.theme.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SectionTitle(title: String) {
    Text(text = title)
}

@Composable
fun Section(title: String, desc: List<String>, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.wrapContentSize(), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.weight(1f)) { SectionTitle(title) }
            TextButton(onClick = { expanded = !expanded }) { Text(if (expanded) "收起说明" else "展开说明") }
        }
        AnimatedVisibility(expanded) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                desc.forEach { Text(it) }
            }
        }
        content()
    }
}

@Composable
fun LabeledSwitch(text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun Token(bg: Color, fg: Color, label: String) {
    androidx.compose.material3.Surface(color = bg, shape = RoundedCornerShape(12.dp)) {
        Box(modifier = Modifier.padding(8.dp).wrapContentSize(), contentAlignment = Alignment.Center) {
            Text(label, color = fg)
        }
    }
}

@Composable
fun ElevatedSurfaceSample(elevated: Boolean) {
    val colors = MaterialTheme.colorScheme
    val cardColors = if (elevated) CardDefaults.elevatedCardColors() else CardDefaults.cardColors()
    Card(colors = cardColors) {
        Box(modifier = Modifier.padding(8.dp).wrapContentSize(), contentAlignment = Alignment.Center) {
            Text(if (elevated) "Elevated" else "Flat", color = colors.onSurface)
        }
    }
}
package com.just.theme.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.just.theme.util.colorToHex
import kotlin.math.roundToInt

@Composable
fun ColorPickerDialog(
    initialColor: Color,
    onConfirm: (Color) -> Unit,
    onDismiss: () -> Unit,
) {
    var r by remember(initialColor) { mutableStateOf((initialColor.red * 255).roundToInt().coerceIn(0, 255)) }
    var g by remember(initialColor) { mutableStateOf((initialColor.green * 255).roundToInt().coerceIn(0, 255)) }
    var b by remember(initialColor) { mutableStateOf((initialColor.blue * 255).roundToInt().coerceIn(0, 255)) }
    var a by remember(initialColor) { mutableStateOf((initialColor.alpha * 255).roundToInt().coerceIn(0, 255)) }
    val current = Color(r / 255f, g / 255f, b / 255f, a / 255f)
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Color") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(color = current, shape = RoundedCornerShape(8.dp)) {
                    Box(modifier = Modifier.size(120.dp, 48.dp)) {}
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("R")
                    Slider(value = r.toFloat(), onValueChange = { r = it.roundToInt().coerceIn(0, 255) }, valueRange = 0f..255f, modifier = Modifier.weight(1f))
                    Text(r.toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("G")
                    Slider(value = g.toFloat(), onValueChange = { g = it.roundToInt().coerceIn(0, 255) }, valueRange = 0f..255f, modifier = Modifier.weight(1f))
                    Text(g.toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("B")
                    Slider(value = b.toFloat(), onValueChange = { b = it.roundToInt().coerceIn(0, 255) }, valueRange = 0f..255f, modifier = Modifier.weight(1f))
                    Text(b.toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("A")
                    Slider(value = a.toFloat(), onValueChange = { a = it.roundToInt().coerceIn(0, 255) }, valueRange = 0f..255f, modifier = Modifier.weight(1f))
                    Text(a.toString())
                }
                Text(colorToHex(current))
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(current) }) { Text("Confirm") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

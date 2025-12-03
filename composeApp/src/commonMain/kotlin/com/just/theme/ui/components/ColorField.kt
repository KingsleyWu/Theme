package com.just.theme.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.just.theme.util.colorToHex
import com.just.theme.util.parseHexColor

@Composable
fun ColorField(label: String, value: String, onChange: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(value = value, onValueChange = onChange, label = { Text(label) }, modifier = Modifier.weight(1f))
        val preview = parseHexColor(value) ?: MaterialTheme.colorScheme.surfaceVariant
        Surface(color = preview, shape = RoundedCornerShape(8.dp)) {
            Box(modifier = Modifier.size(48.dp, 32.dp)) {}
        }
        var pickerOpen by remember { mutableStateOf(false) }
        TextButton(onClick = { pickerOpen = true }) { Text("Picker") }
        if (pickerOpen) {
            ColorPickerDialog(
                initialColor = preview,
                onConfirm = { c ->
                    onChange(colorToHex(c))
                    pickerOpen = false
                },
                onDismiss = { pickerOpen = false }
            )
        }
    }
}

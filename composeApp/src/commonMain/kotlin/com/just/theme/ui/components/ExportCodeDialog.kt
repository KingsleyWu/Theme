package com.just.theme.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import com.just.theme.ui.screens.theme.generateKotlinCode
import com.just.theme.util.toClipEntry
import kotlinx.coroutines.launch

@Composable
fun ExportCodeDialog(scheme: ColorScheme, onDismiss: () -> Unit) {
    val code = remember(scheme) { generateKotlinCode(scheme) }
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Export Theme Code") },
        text = {
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = code,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Kotlin Code") }
                )
                SnackbarHost(
                    hostState = hostState,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                scope.launch {
                    clipboard.setClipEntry(code.toClipEntry())
                    hostState.showSnackbar(message = "Copied", duration = SnackbarDuration.Short)
                }
            }) {
                Text("Copy")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Close") }
        }
    )
}

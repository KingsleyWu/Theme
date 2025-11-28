package com.just.theme.util

import androidx.compose.ui.platform.ClipEntry

expect fun String.toClipEntry(): ClipEntry

/**
 * // commonMain
 * import androidx.compose.ui.platform.LocalClipboard
 *
 * @Composable
 * fun ClipboardReader() {
 *     val clipboard = LocalClipboard.current
 *     val scope = rememberCoroutineScope()
 *     scope.launch { {
 *        val text = clipboard.getClipEntry()?.readText()
 *     }
 * }
 */
expect suspend fun ClipEntry.readText(): String?
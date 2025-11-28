package com.just.theme.util

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry
import kotlinx.coroutines.await
import org.w3c.files.Blob
import org.w3c.files.FileReader
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalComposeUiApi::class)
actual fun String.toClipEntry(): ClipEntry = ClipEntry.withPlainText(this)

@OptIn(ExperimentalComposeUiApi::class)
actual suspend fun ClipEntry.readText(): String? {
    return try {
        val items = clipboardItems
        if (items.isEmpty()) return null
        val item = items[0]
        
        // Check for text/plain
        val hasText = item.types.toList().map { it }.any { it == "text/plain" }
        if (!hasText) return null

        val blob = item.getType("text/plain").await()
        blob.readAsText()
    } catch (e: Exception) {
        null
    }
}

suspend fun Blob.readAsText() = suspendCoroutine { cont ->
    val reader = FileReader()
    reader.onload = {
        cont.resume(reader.result.toString())
    }
    reader.onerror = {
        cont.resume(null)
    }
    reader.readAsText(this)
}

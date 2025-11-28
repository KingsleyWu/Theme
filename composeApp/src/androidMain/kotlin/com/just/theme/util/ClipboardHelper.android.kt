package com.just.theme.util

import android.content.ClipData
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.toClipEntry

actual fun String.toClipEntry(): ClipEntry = ClipData.newPlainText("text", this).toClipEntry()

actual suspend fun ClipEntry.readText(): String? {
    val itemCount = clipData.itemCount
    var textFull = ""
    for (i in 0 until itemCount) {
        val item = clipData.getItemAt(i)
        val text = item?.text
        if (text != null) {
            textFull += text
        }
    }
    return textFull.ifEmpty { null }
}

package com.just.theme.util

import androidx.compose.ui.platform.ClipEntry
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual fun String.toClipEntry(): ClipEntry = ClipEntry(StringSelection(this))

actual suspend fun ClipEntry.readText(): String? = withContext(Dispatchers.IO) {
    try {
        val transferable = nativeClipEntry as? Transferable
        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            transferable.getTransferData(DataFlavor.stringFlavor) as? String
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}

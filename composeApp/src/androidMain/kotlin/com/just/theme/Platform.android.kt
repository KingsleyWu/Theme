package com.just.theme

import android.os.Build
import androidx.compose.ui.platform.ClipEntry
import android.content.ClipData
import androidx.compose.ui.platform.toClipEntry

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun String.toClipEntry(): ClipEntry = ClipData.newPlainText("text", this).toClipEntry()
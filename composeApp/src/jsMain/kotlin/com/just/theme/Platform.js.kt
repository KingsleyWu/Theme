package com.just.theme

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry

class JsPlatform: Platform {
    override val name: String = "Web with Kotlin/JS"
}

actual fun getPlatform(): Platform = JsPlatform()

@OptIn(ExperimentalComposeUiApi::class)
actual fun String.toClipEntry() = ClipEntry.withPlainText(this)
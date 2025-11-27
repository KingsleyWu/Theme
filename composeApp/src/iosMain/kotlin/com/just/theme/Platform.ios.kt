package com.just.theme

import androidx.compose.ui.ExperimentalComposeUiApi
import platform.UIKit.UIDevice
import androidx.compose.ui.platform.ClipEntry

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalComposeUiApi::class)
actual fun String.toClipEntry(): ClipEntry = ClipEntry.withPlainText(this)
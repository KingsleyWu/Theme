package com.just.theme.util

import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.ExperimentalComposeUiApi

@OptIn(ExperimentalComposeUiApi::class)
actual fun String.toClipEntry(): ClipEntry = ClipEntry.withPlainText(this)

@OptIn(ExperimentalComposeUiApi::class)
actual suspend fun ClipEntry.readText(): String? = getPlainText()

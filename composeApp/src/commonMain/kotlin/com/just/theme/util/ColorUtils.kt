package com.just.theme.util

import androidx.compose.ui.graphics.Color
import kotlin.math.pow
import kotlin.math.roundToInt

fun overlayStateColor(base: Color, overlay: Color, alpha: Float): Color =
    blendOver(overlay.copy(alpha = alpha), base)

fun blendOver(top: Color, bottom: Color): Color {
    val a1 = top.alpha
    val a2 = bottom.alpha
    val outA = a1 + a2 * (1f - a1)
    val denom = if (outA == 0f) 1f else outA
    val r = (top.red * a1 + bottom.red * a2 * (1f - a1)) / denom
    val g = (top.green * a1 + bottom.green * a2 * (1f - a1)) / denom
    val b = (top.blue * a1 + bottom.blue * a2 * (1f - a1)) / denom
    return Color(r, g, b, outA)
}

fun parseHexColor(hex: String): Color? {
    val s = hex.trim().removePrefix("#")
    val full = when (s.length) {
        6 -> "FF$s"
        8 -> s
        else -> return null
    }
    fun byteAt(i: Int): Int = full.substring(i, i + 2).toInt(16)
    return try {
        val a = byteAt(0)
        val r = byteAt(2)
        val g = byteAt(4)
        val b = byteAt(6)
        Color(
            red = r / 255f,
            green = g / 255f,
            blue = b / 255f,
            alpha = a / 255f,
        )
    } catch (e: Exception) {
        null
    }
}

fun colorToHex(c: Color): String {
    val a = (c.alpha * 255).roundToInt().coerceIn(0, 255)
    val r = (c.red * 255).roundToInt().coerceIn(0, 255)
    val g = (c.green * 255).roundToInt().coerceIn(0, 255)
    val b = (c.blue * 255).roundToInt().coerceIn(0, 255)
    fun h(n: Int) = n.toString(16).uppercase().padStart(2, '0')
    return "#${h(a)}${h(r)}${h(g)}${h(b)}"
}

fun luminance(color: Color): Float {
    fun linearize(v: Float): Float {
        return if (v <= 0.04045f) v / 12.92f else ((v + 0.055f) / 1.055f).pow(2.4f)
    }
    val r = linearize(color.red)
    val g = linearize(color.green)
    val b = linearize(color.blue)
    return 0.2126f * r + 0.7152f * g + 0.0722f * b
}

fun contrastRatio(c1: Color, c2: Color): Float {
    val l1 = luminance(c1)
    val l2 = luminance(c2)
    val lighter = maxOf(l1, l2)
    val darker = minOf(l1, l2)
    return (lighter + 0.05f) / (darker + 0.05f)
}
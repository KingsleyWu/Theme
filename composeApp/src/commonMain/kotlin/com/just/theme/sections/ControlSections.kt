package com.just.theme.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Close
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.just.theme.ui.Section
import com.just.theme.ui.LabeledSwitch
import com.just.theme.ui.Token
import com.just.theme.ui.ElevatedSurfaceSample
import com.just.theme.util.overlayStateColor
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * 包含所有独立的展示区块（Section）。
 * 每个函数对应 ColorScheme 示例页中的一个卡片，封装了该颜色角色的 UI 展示逻辑。
 */
object ControlSections {
    @Composable
    fun ButtonsSection(
        iconTypeGlobal: Int,
        iconSizeGlobal: Float,
        iconPaddingGlobal: Float,
        onIconTypeChange: (Int) -> Unit,
        onIconSizeChange: (Float) -> Unit,
        onIconPaddingChange: (Float) -> Unit,
    ) {
        val colors = MaterialTheme.colorScheme
        Section(
            title = "按钮形态对比",
            desc = listOf("Filled/Tonal/Outlined/Text 的并列比较。")
        ) {
            var enabled by remember { mutableStateOf(true) }
            var source by remember { mutableStateOf(0) }
            var showFilled by remember { mutableStateOf(true) }
            var showTonal by remember { mutableStateOf(true) }
            var showOutlined by remember { mutableStateOf(true) }
            var showText by remember { mutableStateOf(true) }
            var showIconPlain by remember { mutableStateOf(false) }
            var showIconFilled by remember { mutableStateOf(false) }
            var showIconTonal by remember { mutableStateOf(false) }
            var showIconOutlined by remember { mutableStateOf(false) }
            var radius by remember { mutableStateOf(12f) }
            var outline by remember { mutableStateOf(1f) }
            var iconOutlinedRadius by remember { mutableStateOf(12f) }
            var iconOutline by remember { mutableStateOf(1f) }
            var elevIndex by remember { mutableStateOf(0) }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LabeledSwitch("启用", enabled) { enabled = it }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = source == 0, onClick = { source = 0 })
                        Text("Primary")
                        RadioButton(selected = source == 1, onClick = { source = 1 })
                        Text("Secondary")
                        RadioButton(selected = source == 2, onClick = { source = 2 })
                        Text("Tertiary")
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("Filled", showFilled) { showFilled = it }
                    LabeledSwitch("Tonal", showTonal) { showTonal = it }
                    LabeledSwitch("Outlined", showOutlined) { showOutlined = it }
                    LabeledSwitch("Text", showText) { showText = it }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("Icon", showIconPlain) { showIconPlain = it }
                    LabeledSwitch("IconFilled", showIconFilled) { showIconFilled = it }
                    LabeledSwitch("IconTonal", showIconTonal) { showIconTonal = it }
                    LabeledSwitch("IconOutlined", showIconOutlined) { showIconOutlined = it }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("圆角")
                    Slider(value = radius, onValueChange = { radius = it }, valueRange = 0f..32f, modifier = Modifier.weight(1f))
                    Text(radius.roundToInt().toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("轮廓")
                    Slider(value = outline, onValueChange = { outline = it }, valueRange = 0f..4f, modifier = Modifier.weight(1f))
                    Text(outline.roundToInt().toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("图标角半径(Outlined)")
                    Slider(value = iconOutlinedRadius, onValueChange = { iconOutlinedRadius = it }, valueRange = 0f..32f, modifier = Modifier.weight(1f))
                    Text(iconOutlinedRadius.roundToInt().toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("图标边框")
                    Slider(value = iconOutline, onValueChange = { iconOutline = it }, valueRange = 0f..4f, modifier = Modifier.weight(1f))
                    Text(iconOutline.roundToInt().toString())
                    Text("图标大小")
                    Slider(value = iconSizeGlobal, onValueChange = onIconSizeChange, valueRange = 12f..48f, modifier = Modifier.weight(1f))
                    Text(iconSizeGlobal.roundToInt().toString())
                    Text("内边距")
                    Slider(value = iconPaddingGlobal, onValueChange = onIconPaddingChange, valueRange = 0f..24f, modifier = Modifier.weight(1f))
                    Text(iconPaddingGlobal.roundToInt().toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("图标类型")
                    RadioButton(selected = iconTypeGlobal == 0, onClick = { onIconTypeChange(0) })
                    Text("Star")
                    RadioButton(selected = iconTypeGlobal == 1, onClick = { onIconTypeChange(1) })
                    Text("Add")
                    RadioButton(selected = iconTypeGlobal == 2, onClick = { onIconTypeChange(2) })
                    Text("Favorite")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = iconTypeGlobal == 3, onClick = { onIconTypeChange(3) })
                    Text("Search")
                    RadioButton(selected = iconTypeGlobal == 4, onClick = { onIconTypeChange(4) })
                    Text("Settings")
                    RadioButton(selected = iconTypeGlobal == 5, onClick = { onIconTypeChange(5) })
                    Text("Share")
                    RadioButton(selected = iconTypeGlobal == 6, onClick = { onIconTypeChange(6) })
                    Text("Close")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("海拔")
                    Slider(value = elevIndex.toFloat(), onValueChange = { elevIndex = it.roundToInt().coerceIn(0, 3) }, valueRange = 0f..3f, steps = 3, modifier = Modifier.weight(1f))
                    val elevLabel = listOf(0, 2, 6, 8)[elevIndex]
                    Text("${elevLabel}dp")
                }
                val accent = when (source) { 0 -> colors.primary; 1 -> colors.secondary; else -> colors.tertiary }
                val onAccent = when (source) { 0 -> colors.onPrimary; 1 -> colors.onSecondary; else -> colors.onTertiary }
                val accentContainer = when (source) { 0 -> colors.primaryContainer; 1 -> colors.secondaryContainer; else -> colors.tertiaryContainer }
                val onAccentContainer = when (source) { 0 -> colors.onPrimaryContainer; 1 -> colors.onSecondaryContainer; else -> colors.onTertiaryContainer }
                val iconVector = when (iconTypeGlobal) {
                    0 -> Icons.Filled.Star
                    1 -> Icons.Filled.Add
                    2 -> Icons.Filled.Favorite
                    3 -> Icons.Filled.Search
                    4 -> Icons.Filled.Settings
                    5 -> Icons.Filled.Share
                    else -> Icons.Filled.Close
                }
                val shape = RoundedCornerShape(radius.roundToInt().dp)
                val elevDp = listOf(0.dp, 2.dp, 6.dp, 8.dp)[elevIndex]
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    if (showFilled) Button(onClick = {}, enabled = enabled, shape = shape, colors = ButtonDefaults.buttonColors(containerColor = accent, contentColor = onAccent), elevation = ButtonDefaults.buttonElevation(defaultElevation = elevDp)) { Text("Filled") }
                    if (showTonal) FilledTonalButton(onClick = {}, enabled = enabled, shape = shape, colors = ButtonDefaults.filledTonalButtonColors(containerColor = accentContainer, contentColor = onAccentContainer), elevation = ButtonDefaults.buttonElevation(defaultElevation = elevDp)) { Text("Tonal") }
                    if (showOutlined) OutlinedButton(onClick = {}, enabled = enabled, shape = shape, border = BorderStroke(outline.dp, accent), colors = ButtonDefaults.outlinedButtonColors(contentColor = accent), elevation = ButtonDefaults.buttonElevation(defaultElevation = elevDp)) { Text("Outlined") }
                    if (showText) TextButton(onClick = {}, enabled = enabled, shape = shape, colors = ButtonDefaults.textButtonColors(contentColor = accent), elevation = ButtonDefaults.buttonElevation(defaultElevation = elevDp)) { Text("Text") }
                    if (showIconPlain) IconButton(onClick = {}, enabled = enabled, colors = IconButtonDefaults.iconButtonColors(contentColor = accent)) { Icon(iconVector, contentDescription = null, modifier = Modifier.padding(iconPaddingGlobal.dp).size(iconSizeGlobal.dp)) }
                    if (showIconFilled) FilledIconButton(onClick = {}, enabled = enabled, colors = IconButtonDefaults.filledIconButtonColors(containerColor = accentContainer, contentColor = onAccentContainer)) { Icon(iconVector, contentDescription = null, modifier = Modifier.padding(iconPaddingGlobal.dp).size(iconSizeGlobal.dp)) }
                    if (showIconTonal) FilledTonalIconButton(onClick = {}, enabled = enabled, colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = accentContainer, contentColor = onAccentContainer)) { Icon(iconVector, contentDescription = null, modifier = Modifier.padding(iconPaddingGlobal.dp).size(iconSizeGlobal.dp)) }
                    if (showIconOutlined) OutlinedIconButton(onClick = {}, enabled = enabled, colors = IconButtonDefaults.outlinedIconButtonColors(contentColor = accent), border = BorderStroke(iconOutline.dp, accent), shape = RoundedCornerShape(iconOutlinedRadius.roundToInt().dp)) { Icon(iconVector, contentDescription = null, modifier = Modifier.padding(iconPaddingGlobal.dp).size(iconSizeGlobal.dp)) }
                    Token(accent, onAccent, "accent")
                    Token(accentContainer, onAccentContainer, "accentContainer")
                }
            }
        }
    }

    @Composable
    fun InteractionSection(
        iconTypeGlobal: Int,
        iconSizeGlobal: Float,
        iconPaddingGlobal: Float,
        onIconSizeChange: (Float) -> Unit,
        onIconPaddingChange: (Float) -> Unit,
    ) {
        val colors = MaterialTheme.colorScheme
        Section(
            title = "交互态与状态层",
            desc = listOf("Pressed/Focused/Hovered 的状态层颜色对比。")
        ) {
            var enabled by remember { mutableStateOf(true) }
            var source by remember { mutableStateOf(0) }
            var showFilled by remember { mutableStateOf(true) }
            var showTonal by remember { mutableStateOf(true) }
            var showOutlined by remember { mutableStateOf(true) }
            var showText by remember { mutableStateOf(true) }
            var showIconPlain by remember { mutableStateOf(false) }
            var showIconFilled by remember { mutableStateOf(false) }
            var showIconTonal by remember { mutableStateOf(false) }
            var showIconOutlined by remember { mutableStateOf(false) }
            var btnElevation by remember { mutableStateOf(0f) }
            var btnOutline by remember { mutableStateOf(1f) }
            var btnRadius by remember { mutableStateOf(12f) }
            var iconOutline2 by remember { mutableStateOf(1f) }
            var pressed by remember { mutableStateOf(false) }
            var focused by remember { mutableStateOf(false) }
            var hovered by remember { mutableStateOf(false) }
            val isrc = remember { MutableInteractionSource() }
            var pressRef by remember { mutableStateOf<PressInteraction.Press?>(null) }
            var focusRef by remember { mutableStateOf<FocusInteraction.Focus?>(null) }
            var hoverRef by remember { mutableStateOf<HoverInteraction.Enter?>(null) }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("启用", enabled) { enabled = it }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = source == 0, onClick = { source = 0 })
                        Text("Primary")
                        RadioButton(selected = source == 1, onClick = { source = 1 })
                        Text("Secondary")
                        RadioButton(selected = source == 2, onClick = { source = 2 })
                        Text("Tertiary")
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("Filled", showFilled) { showFilled = it }
                    LabeledSwitch("Tonal", showTonal) { showTonal = it }
                    LabeledSwitch("Outlined", showOutlined) { showOutlined = it }
                    LabeledSwitch("Text", showText) { showText = it }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("按钮海拔")
                    Slider(value = btnElevation, onValueChange = { btnElevation = it }, valueRange = 0f..16f, modifier = Modifier.weight(1f))
                    Text("${btnElevation.toInt()}dp")
                    Text("按钮圆角")
                    Slider(value = btnRadius, onValueChange = { btnRadius = it }, valueRange = 0f..32f, modifier = Modifier.weight(1f))
                    Text(btnRadius.toInt().toString())
                    Text("按钮描边")
                    Slider(value = btnOutline, onValueChange = { btnOutline = it }, valueRange = 0f..8f, modifier = Modifier.weight(1f))
                    Text(btnOutline.toInt().toString())
                    Text("图标描边")
                    Slider(value = iconOutline2, onValueChange = { iconOutline2 = it }, valueRange = 0f..8f, modifier = Modifier.weight(1f))
                    Text(iconOutline2.toInt().toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("Icon", showIconPlain) { showIconPlain = it }
                    LabeledSwitch("IconFilled", showIconFilled) { showIconFilled = it }
                    LabeledSwitch("IconTonal", showIconTonal) { showIconTonal = it }
                    LabeledSwitch("IconOutlined", showIconOutlined) { showIconOutlined = it }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("Pressed", pressed) {
                        pressed = it
                        if (it) {
                            val p = PressInteraction.Press(Offset.Zero)
                            pressRef = p
                            isrc.tryEmit(p)
                        } else {
                            pressRef?.let { pr -> isrc.tryEmit(PressInteraction.Release(pr)) }
                            pressRef = null
                        }
                    }
                    LabeledSwitch("Focused", focused) {
                        focused = it
                        if (it) {
                            val f = FocusInteraction.Focus()
                            focusRef = f
                            isrc.tryEmit(f)
                        } else {
                            focusRef?.let { fr -> isrc.tryEmit(FocusInteraction.Unfocus(fr)) }
                            focusRef = null
                        }
                    }
                    LabeledSwitch("Hovered", hovered) {
                        hovered = it
                        if (it) {
                            val h = HoverInteraction.Enter()
                            hoverRef = h
                            isrc.tryEmit(h)
                        } else {
                            hoverRef?.let { hr -> isrc.tryEmit(HoverInteraction.Exit(hr)) }
                            hoverRef = null
                        }
                    }
                }
                val accent = when (source) { 0 -> colors.primary; 1 -> colors.secondary; else -> colors.tertiary }
                val onAccent = when (source) { 0 -> colors.onPrimary; 1 -> colors.onSecondary; else -> colors.onTertiary }
                val accentContainer = when (source) { 0 -> colors.primaryContainer; 1 -> colors.secondaryContainer; else -> colors.tertiaryContainer }
                val onAccentContainer = when (source) { 0 -> colors.onPrimaryContainer; 1 -> colors.onSecondaryContainer; else -> colors.onTertiaryContainer }
                val btnShape = RoundedCornerShape(btnRadius.toInt().dp)
                val btnElevDp = btnElevation.toInt().dp
                val btnOutlineDp = btnOutline.toInt().dp
                val iconOutlineDp = iconOutline2.toInt().dp
                val iconVector = when (iconTypeGlobal) {
                    0 -> Icons.Filled.Star
                    1 -> Icons.Filled.Add
                    2 -> Icons.Filled.Favorite
                    3 -> Icons.Filled.Search
                    4 -> Icons.Filled.Settings
                    5 -> Icons.Filled.Share
                    else -> Icons.Filled.Close
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    if (showFilled) Button(onClick = {}, enabled = enabled, interactionSource = isrc, shape = btnShape, colors = ButtonDefaults.buttonColors(containerColor = accent, contentColor = onAccent), elevation = ButtonDefaults.buttonElevation(defaultElevation = btnElevDp)) { Text("Filled") }
                    if (showTonal) FilledTonalButton(onClick = {}, enabled = enabled, interactionSource = isrc, shape = btnShape, colors = ButtonDefaults.filledTonalButtonColors(containerColor = accentContainer, contentColor = onAccentContainer), elevation = ButtonDefaults.buttonElevation(defaultElevation = btnElevDp)) { Text("Tonal") }
                    if (showOutlined) OutlinedButton(onClick = {}, enabled = enabled, interactionSource = isrc, shape = btnShape, colors = ButtonDefaults.outlinedButtonColors(contentColor = accent), border = BorderStroke(btnOutlineDp, accent), elevation = ButtonDefaults.buttonElevation(defaultElevation = btnElevDp)) { Text("Outlined") }
                    if (showText) TextButton(onClick = {}, enabled = enabled, interactionSource = isrc, shape = btnShape, colors = ButtonDefaults.textButtonColors(contentColor = accent), elevation = ButtonDefaults.buttonElevation(defaultElevation = btnElevDp)) { Text("Text") }
                    if (showIconPlain) IconButton(onClick = {}, enabled = enabled, interactionSource = isrc, colors = IconButtonDefaults.iconButtonColors(contentColor = accent)) { Icon(iconVector, contentDescription = null, modifier = Modifier.padding(iconPaddingGlobal.dp).size(iconSizeGlobal.dp)) }
                    if (showIconFilled) FilledIconButton(onClick = {}, enabled = enabled, interactionSource = isrc, colors = IconButtonDefaults.filledIconButtonColors(containerColor = accentContainer, contentColor = onAccentContainer)) { Icon(iconVector, contentDescription = null, modifier = Modifier.padding(iconPaddingGlobal.dp).size(iconSizeGlobal.dp)) }
                    if (showIconTonal) FilledTonalIconButton(onClick = {}, enabled = enabled, interactionSource = isrc, colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = accentContainer, contentColor = onAccentContainer)) { Icon(iconVector, contentDescription = null, modifier = Modifier.padding(iconPaddingGlobal.dp).size(iconSizeGlobal.dp)) }
                    if (showIconOutlined) OutlinedIconButton(onClick = {}, enabled = enabled, interactionSource = isrc, colors = IconButtonDefaults.outlinedIconButtonColors(contentColor = accent), border = BorderStroke(iconOutlineDp, accent), shape = btnShape) { Icon(iconVector, contentDescription = null, modifier = Modifier.padding(iconPaddingGlobal.dp).size(iconSizeGlobal.dp)) }
                }
                val pressedAlpha = 0.12f
                val focusedAlpha = 0.12f
                val hoveredAlpha = 0.08f
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(accent, onAccent, pressedAlpha), onAccent, "accent Pressed")
                    Token(overlayStateColor(accentContainer, onAccentContainer, pressedAlpha), onAccentContainer, "accentContainer Pressed")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(accent, onAccent, focusedAlpha), onAccent, "accent Focused")
                    Token(overlayStateColor(accentContainer, onAccentContainer, focusedAlpha), onAccentContainer, "accentContainer Focused")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(accent, onAccent, hoveredAlpha), onAccent, "accent Hovered")
                    Token(overlayStateColor(accentContainer, onAccentContainer, hoveredAlpha), onAccentContainer, "accentContainer Hovered")
                }

                var contLevel by remember { mutableStateOf(2) }
                Text("容器层级")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = contLevel == 0, onClick = { contLevel = 0 })
                    Text("lowest")
                    RadioButton(selected = contLevel == 1, onClick = { contLevel = 1 })
                    Text("low")
                    RadioButton(selected = contLevel == 2, onClick = { contLevel = 2 })
                    Text("base")
                    RadioButton(selected = contLevel == 3, onClick = { contLevel = 3 })
                    Text("high")
                    RadioButton(selected = contLevel == 4, onClick = { contLevel = 4 })
                    Text("highest")
                }
                val surfaceBase = colors.surface
                val surfaceContent = colors.onSurface
                val variantBase = colors.surfaceVariant
                val variantContent = colors.onSurfaceVariant
                val inverseBase = colors.inverseSurface
                val inverseContent = colors.inverseOnSurface
                val contBase = when (contLevel) {
                    0 -> colors.surfaceContainerLowest
                    1 -> colors.surfaceContainerLow
                    2 -> colors.surfaceContainer
                    3 -> colors.surfaceContainerHigh
                    else -> colors.surfaceContainerHighest
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(surfaceBase, surfaceContent, pressedAlpha), surfaceContent, "surface Pressed")
                    Token(overlayStateColor(contBase, surfaceContent, pressedAlpha), surfaceContent, "surfaceContainer Pressed")
                    Token(overlayStateColor(variantBase, variantContent, pressedAlpha), variantContent, "surfaceVariant Pressed")
                    Token(overlayStateColor(inverseBase, inverseContent, pressedAlpha), inverseContent, "inverseSurface Pressed")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(surfaceBase, surfaceContent, focusedAlpha), surfaceContent, "surface Focused")
                    Token(overlayStateColor(contBase, surfaceContent, focusedAlpha), surfaceContent, "surfaceContainer Focused")
                    Token(overlayStateColor(variantBase, variantContent, focusedAlpha), variantContent, "surfaceVariant Focused")
                    Token(overlayStateColor(inverseBase, inverseContent, focusedAlpha), inverseContent, "inverseSurface Focused")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(surfaceBase, surfaceContent, hoveredAlpha), surfaceContent, "surface Hovered")
                    Token(overlayStateColor(contBase, surfaceContent, hoveredAlpha), surfaceContent, "surfaceContainer Hovered")
                    Token(overlayStateColor(variantBase, variantContent, hoveredAlpha), variantContent, "surfaceVariant Hovered")
                    Token(overlayStateColor(inverseBase, inverseContent, hoveredAlpha), inverseContent, "inverseSurface Hovered")
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(colors.error, colors.onError, pressedAlpha), colors.onError, "error Pressed")
                    Token(overlayStateColor(colors.errorContainer, colors.onErrorContainer, pressedAlpha), colors.onErrorContainer, "errorContainer Pressed")
                    Token(overlayStateColor(colors.background, colors.onBackground, pressedAlpha), colors.onBackground, "background Pressed")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(colors.error, colors.onError, focusedAlpha), colors.onError, "error Focused")
                    Token(overlayStateColor(colors.errorContainer, colors.onErrorContainer, focusedAlpha), colors.onErrorContainer, "errorContainer Focused")
                    Token(overlayStateColor(colors.background, colors.onBackground, focusedAlpha), colors.onBackground, "background Focused")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(colors.error, colors.onError, hoveredAlpha), colors.onError, "error Hovered")
                    Token(overlayStateColor(colors.errorContainer, colors.onErrorContainer, hoveredAlpha), colors.onErrorContainer, "errorContainer Hovered")
                    Token(overlayStateColor(colors.background, colors.onBackground, hoveredAlpha), colors.onBackground, "background Hovered")
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(colors.primaryFixed, colors.onPrimaryFixed, pressedAlpha), colors.onPrimaryFixed, "primaryFixed Pressed")
                    Token(overlayStateColor(colors.primaryFixedDim, colors.onPrimaryFixedVariant, pressedAlpha), colors.onPrimaryFixedVariant, "primaryFixedDim Pressed")
                    Token(overlayStateColor(colors.secondaryFixed, colors.onSecondaryFixed, pressedAlpha), colors.onSecondaryFixed, "secondaryFixed Pressed")
                    Token(overlayStateColor(colors.secondaryFixedDim, colors.onSecondaryFixedVariant, pressedAlpha), colors.onSecondaryFixedVariant, "secondaryFixedDim Pressed")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(colors.primaryFixed, colors.onPrimaryFixed, focusedAlpha), colors.onPrimaryFixed, "primaryFixed Focused")
                    Token(overlayStateColor(colors.primaryFixedDim, colors.onPrimaryFixedVariant, focusedAlpha), colors.onPrimaryFixedVariant, "primaryFixedDim Focused")
                    Token(overlayStateColor(colors.secondaryFixed, colors.onSecondaryFixed, focusedAlpha), colors.onSecondaryFixed, "secondaryFixed Focused")
                    Token(overlayStateColor(colors.secondaryFixedDim, colors.onSecondaryFixedVariant, focusedAlpha), colors.onSecondaryFixedVariant, "secondaryFixedDim Focused")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(colors.primaryFixed, colors.onPrimaryFixed, hoveredAlpha), colors.onPrimaryFixed, "primaryFixed Hovered")
                    Token(overlayStateColor(colors.primaryFixedDim, colors.onPrimaryFixedVariant, hoveredAlpha), colors.onPrimaryFixedVariant, "primaryFixedDim Hovered")
                    Token(overlayStateColor(colors.secondaryFixed, colors.onSecondaryFixed, hoveredAlpha), colors.onSecondaryFixed, "secondaryFixed Hovered")
                    Token(overlayStateColor(colors.secondaryFixedDim, colors.onSecondaryFixedVariant, hoveredAlpha), colors.onSecondaryFixedVariant, "secondaryFixedDim Hovered")
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(colors.tertiaryFixed, colors.onTertiaryFixed, pressedAlpha), colors.onTertiaryFixed, "tertiaryFixed Pressed")
                    Token(overlayStateColor(colors.tertiaryFixedDim, colors.onTertiaryFixedVariant, pressedAlpha), colors.onTertiaryFixedVariant, "tertiaryFixedDim Pressed")
                    Token(overlayStateColor(colors.inversePrimary, colors.inverseOnSurface, pressedAlpha), colors.inverseOnSurface, "inversePrimary Pressed")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(colors.tertiaryFixed, colors.onTertiaryFixed, focusedAlpha), colors.onTertiaryFixed, "tertiaryFixed Focused")
                    Token(overlayStateColor(colors.tertiaryFixedDim, colors.onTertiaryFixedVariant, focusedAlpha), colors.onTertiaryFixedVariant, "tertiaryFixedDim Focused")
                    Token(overlayStateColor(colors.inversePrimary, colors.inverseOnSurface, focusedAlpha), colors.inverseOnSurface, "inversePrimary Focused")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(overlayStateColor(colors.tertiaryFixed, colors.onTertiaryFixed, hoveredAlpha), colors.onTertiaryFixed, "tertiaryFixed Hovered")
                    Token(overlayStateColor(colors.tertiaryFixedDim, colors.onTertiaryFixedVariant, hoveredAlpha), colors.onTertiaryFixedVariant, "tertiaryFixedDim Hovered")
                    Token(overlayStateColor(colors.inversePrimary, colors.inverseOnSurface, hoveredAlpha), colors.inverseOnSurface, "inversePrimary Hovered")
                }

                var previewFamily by remember { mutableStateOf(0) }
                var previewWidth by remember { mutableStateOf(160f) }
                var previewHeight by remember { mutableStateOf(60f) }
                var previewRadius by remember { mutableStateOf(12f) }
                var previewShadow by remember { mutableStateOf(0f) }
                var previewTonal by remember { mutableStateOf(0f) }
                var previewOutline by remember { mutableStateOf(0f) }
                var borderSource by remember { mutableStateOf(0) }
                var useElevatedCard by remember { mutableStateOf(false) }
                var shapePreset by remember { mutableStateOf(0) }
                Text("预览容器")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = previewFamily == 5, onClick = { previewFamily = 5 })
                    Text("accentContainer")
                    RadioButton(selected = previewFamily == 6, onClick = { previewFamily = 6 })
                    Text("error")
                    RadioButton(selected = previewFamily == 7, onClick = { previewFamily = 7 })
                    Text("errorContainer")
                    RadioButton(selected = previewFamily == 8, onClick = { previewFamily = 8 })
                    Text("background")
                    RadioButton(selected = previewFamily == 0, onClick = { previewFamily = 0 })
                    Text("surface")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = previewFamily == 1, onClick = { previewFamily = 1 })
                    Text("surfaceContainer")
                    RadioButton(selected = previewFamily == 2, onClick = { previewFamily = 2 })
                    Text("surfaceVariant")
                    RadioButton(selected = previewFamily == 3, onClick = { previewFamily = 3 })
                    Text("inverseSurface")
                    RadioButton(selected = previewFamily == 4, onClick = { previewFamily = 4 })
                    Text("accent")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = previewFamily == 9, onClick = { previewFamily = 9 })
                    Text("tertiaryFixed")
                    RadioButton(selected = previewFamily == 10, onClick = { previewFamily = 10 })
                    Text("tertiaryFixedDim")
                    RadioButton(selected = previewFamily == 11, onClick = { previewFamily = 11 })
                    Text("inversePrimary")
                    RadioButton(selected = previewFamily == 12, onClick = { previewFamily = 12 })
                    Text("primaryFixed")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = previewFamily == 13, onClick = { previewFamily = 13 })
                    Text("primaryFixedDim")
                    RadioButton(selected = previewFamily == 14, onClick = { previewFamily = 14 })
                    Text("secondaryFixed")
                    RadioButton(selected = previewFamily == 15, onClick = { previewFamily = 15 })
                    Text("secondaryFixedDim")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("预览宽度")
                    Slider(value = previewWidth, onValueChange = { previewWidth = it }, valueRange = 80f..320f, modifier = Modifier.weight(1f))
                    Text(previewWidth.toInt().toString())
                    Text("预览高度")
                    Slider(value = previewHeight, onValueChange = { previewHeight = it }, valueRange = 40f..200f, modifier = Modifier.weight(1f))
                    Text(previewHeight.toInt().toString())
                    Text("圆角")
                    Slider(value = previewRadius, onValueChange = { previewRadius = it }, valueRange = 0f..48f, modifier = Modifier.weight(1f))
                    Text(previewRadius.toInt().toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("海拔")
                    Slider(value = previewShadow, onValueChange = { previewShadow = it }, valueRange = 0f..16f, modifier = Modifier.weight(1f))
                    Text("${'$'}{previewShadow.toInt()}dp")
                    Text("Tonal")
                    Slider(value = previewTonal, onValueChange = { previewTonal = it }, valueRange = 0f..16f, modifier = Modifier.weight(1f))
                    Text("${'$'}{previewTonal.toInt()}dp")
                    Text("描边")
                    Slider(value = previewOutline, onValueChange = { previewOutline = it }, valueRange = 0f..8f, modifier = Modifier.weight(1f))
                    Text(previewOutline.toInt().toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = borderSource == 0, onClick = { borderSource = 0 })
                    Text("outline")
                    RadioButton(selected = borderSource == 1, onClick = { borderSource = 1 })
                    Text("accent")
                    RadioButton(selected = borderSource == 2, onClick = { borderSource = 2 })
                    Text("onSurface")
                    LabeledSwitch("ElevatedCard", useElevatedCard) { useElevatedCard = it }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("形状预设")
                    RadioButton(selected = shapePreset == 0, onClick = { shapePreset = 0 })
                    Text("Rounded")
                    RadioButton(selected = shapePreset == 1, onClick = { shapePreset = 1 })
                    Text("Cut")
                    RadioButton(selected = shapePreset == 2, onClick = { shapePreset = 2 })
                    Text("Circle")
                }
                val previewBase = when (previewFamily) {
                    0 -> surfaceBase
                    1 -> contBase
                    2 -> variantBase
                    3 -> inverseBase
                    4 -> accent
                    5 -> accentContainer
                    6 -> colors.error
                    7 -> colors.errorContainer
                    8 -> colors.background
                    9 -> colors.tertiaryFixed
                    10 -> colors.tertiaryFixedDim
                    12 -> colors.primaryFixed
                    13 -> colors.primaryFixedDim
                    14 -> colors.secondaryFixed
                    15 -> colors.secondaryFixedDim
                    else -> colors.inversePrimary
                }
                val previewContent = when (previewFamily) {
                    0, 1 -> surfaceContent
                    2 -> variantContent
                    3 -> inverseContent
                    4 -> onAccent
                    5 -> onAccentContainer
                    6 -> colors.onError
                    7 -> colors.onErrorContainer
                    8 -> colors.onBackground
                    9 -> colors.onTertiaryFixed
                    10 -> colors.onTertiaryFixedVariant
                    12 -> colors.onPrimaryFixed
                    13 -> colors.onPrimaryFixedVariant
                    14 -> colors.onSecondaryFixed
                    15 -> colors.onSecondaryFixedVariant
                    else -> colors.inverseOnSurface
                }
                val borderColor = when (borderSource) {
                    0 -> colors.outline
                    1 -> accent
                    else -> surfaceContent
                }
                val previewShape = when (shapePreset) { 0 -> RoundedCornerShape(previewRadius.toInt().dp); 1 -> CutCornerShape(previewRadius.toInt().dp); else -> CircleShape }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(color = overlayStateColor(previewBase, previewContent, pressedAlpha), shape = previewShape, shadowElevation = previewShadow.toInt().dp, tonalElevation = previewTonal.toInt().dp, border = if (previewOutline > 0f) BorderStroke(previewOutline.toInt().dp, borderColor) else null) {
                        Box(modifier = Modifier.size(previewWidth.toInt().dp, previewHeight.toInt().dp), contentAlignment = Alignment.Center) { Text("Surface Pressed", color = previewContent) }
                    }
                    if (useElevatedCard) ElevatedCard(colors = CardDefaults.elevatedCardColors(containerColor = overlayStateColor(previewBase, previewContent, pressedAlpha)), shape = previewShape, elevation = CardDefaults.elevatedCardElevation(defaultElevation = previewShadow.toInt().dp)) {
                        Box(modifier = Modifier.size(previewWidth.toInt().dp, previewHeight.toInt().dp), contentAlignment = Alignment.Center) { Text("ElevatedCard Pressed", color = previewContent) }
                    } else Card(colors = CardDefaults.cardColors(containerColor = overlayStateColor(previewBase, previewContent, pressedAlpha)), shape = previewShape, elevation = CardDefaults.cardElevation(defaultElevation = previewShadow.toInt().dp)) {
                        Box(modifier = Modifier.size(previewWidth.toInt().dp, previewHeight.toInt().dp), contentAlignment = Alignment.Center) { Text("Card Pressed", color = previewContent) }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(color = overlayStateColor(previewBase, previewContent, focusedAlpha), shape = previewShape, shadowElevation = previewShadow.toInt().dp, tonalElevation = previewTonal.toInt().dp, border = if (previewOutline > 0f) BorderStroke(previewOutline.toInt().dp, borderColor) else null) {
                        Box(modifier = Modifier.size(previewWidth.toInt().dp, previewHeight.toInt().dp), contentAlignment = Alignment.Center) { Text("Surface Focused", color = previewContent) }
                    }
                    if (useElevatedCard) ElevatedCard(colors = CardDefaults.elevatedCardColors(containerColor = overlayStateColor(previewBase, previewContent, focusedAlpha)), shape = previewShape, elevation = CardDefaults.elevatedCardElevation(defaultElevation = previewShadow.toInt().dp)) {
                        Box(modifier = Modifier.size(previewWidth.toInt().dp, previewHeight.toInt().dp), contentAlignment = Alignment.Center) { Text("ElevatedCard Focused", color = previewContent) }
                    } else Card(colors = CardDefaults.cardColors(containerColor = overlayStateColor(previewBase, previewContent, focusedAlpha)), shape = previewShape, elevation = CardDefaults.cardElevation(defaultElevation = previewShadow.toInt().dp)) {
                        Box(modifier = Modifier.size(previewWidth.toInt().dp, previewHeight.toInt().dp), contentAlignment = Alignment.Center) { Text("Card Focused", color = previewContent) }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(color = overlayStateColor(previewBase, previewContent, hoveredAlpha), shape = previewShape, shadowElevation = previewShadow.toInt().dp, tonalElevation = previewTonal.toInt().dp, border = if (previewOutline > 0f) BorderStroke(previewOutline.toInt().dp, borderColor) else null) {
                        Box(modifier = Modifier.size(previewWidth.toInt().dp, previewHeight.toInt().dp), contentAlignment = Alignment.Center) { Text("Surface Hovered", color = previewContent) }
                    }
                    if (useElevatedCard) ElevatedCard(colors = CardDefaults.elevatedCardColors(containerColor = overlayStateColor(previewBase, previewContent, hoveredAlpha)), shape = previewShape, elevation = CardDefaults.elevatedCardElevation(defaultElevation = previewShadow.toInt().dp)) {
                        Box(modifier = Modifier.size(previewWidth.toInt().dp, previewHeight.toInt().dp), contentAlignment = Alignment.Center) { Text("ElevatedCard Hovered", color = previewContent) }
                    } else Card(colors = CardDefaults.cardColors(containerColor = overlayStateColor(previewBase, previewContent, hoveredAlpha)), shape = previewShape, elevation = CardDefaults.cardElevation(defaultElevation = previewShadow.toInt().dp)) {
                        Box(modifier = Modifier.size(previewWidth.toInt().dp, previewHeight.toInt().dp), contentAlignment = Alignment.Center) { Text("Card Hovered", color = previewContent) }
                    }
                }
            }
        }
    }

    @Composable
    fun PrimaryFamilySection() {
        val colors = MaterialTheme.colorScheme
        Section(
            title = "Primary 家族",
            desc = listOf(
                "primary：主色。用于高强调交互控件容器（如 Button、选中态）。",
                "onPrimary：主色容器上的文本/图标色，保证对比度。",
                "primaryContainer：主色容器背景，适合卡片或大面积强调背景。",
                "onPrimaryContainer：主色容器上的文本/图标色。",
                "inversePrimary：反色环境下的强调动作色（例如 Snackbar 的 action）。",
            )
        ) {
            var enabled by remember { mutableStateOf(true) }
            LabeledSwitch("启用", enabled) { enabled = it }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {}, enabled = enabled) { Text("Button") }
                TextButton(onClick = {}, enabled = enabled) { Text("TextButton") }
                Token(colors.primary, colors.onPrimary, "primary/onPrimary")
                Token(colors.primaryContainer, colors.onPrimaryContainer, "primaryContainer/onPrimaryContainer")
            }
        }
    }

    @Composable
    fun SecondaryFamilySection() {
        val colors = MaterialTheme.colorScheme
        Section(
            title = "Secondary 家族",
            desc = listOf(
                "secondary：次强调色。用于次要交互或选中态的容器色。",
                "onSecondary：次强调容器上的文本/图标色。",
                "secondaryContainer：次强调容器背景（如 FilledTonalButton）。",
                "onSecondaryContainer：次强调容器上的文本/图标色。",
            )
        ) {
            var enabled by remember { mutableStateOf(true) }
            var useContainer by remember { mutableStateOf(true) }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                LabeledSwitch("启用", enabled) { enabled = it }
                LabeledSwitch("容器变体", useContainer) { useContainer = it }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                if (useContainer) {
                    FilledTonalButton(onClick = {}, enabled = enabled) { Text("FilledTonal") }
                } else {
                    Button(onClick = {}, enabled = enabled, colors = ButtonDefaults.buttonColors(containerColor = colors.secondary, contentColor = colors.onSecondary)) { Text("Secondary Button") }
                }
                FloatingActionButton(onClick = {}, containerColor = if (useContainer) colors.secondaryContainer else colors.secondary, contentColor = if (useContainer) colors.onSecondaryContainer else colors.onSecondary) { Text("+") }
                Token(colors.secondary, colors.onSecondary, "secondary/onSecondary")
                Token(colors.secondaryContainer, colors.onSecondaryContainer, "secondaryContainer/onSecondaryContainer")
            }
        }
    }

    @Composable
    fun TertiaryFamilySection() {
        val colors = MaterialTheme.colorScheme
        Section(
            title = "Tertiary 家族",
            desc = listOf(
                "tertiary：第三强调色。用于差异化点缀或分类的强调。",
                "onTertiary：第三强调容器上的文本/图标色。",
                "tertiaryContainer：第三强调容器背景。",
                "onTertiaryContainer：第三强调容器上的文本/图标色。",
            )
        ) {
            var useContainer by remember { mutableStateOf(true) }
            LabeledSwitch("容器变体", useContainer) { useContainer = it }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                val container = if (useContainer) colors.tertiaryContainer else colors.tertiary
                val content = if (useContainer) colors.onTertiaryContainer else colors.onTertiary
                Card(colors = CardDefaults.cardColors(containerColor = container)) {
                    Box(modifier = Modifier.size(140.dp, 60.dp), contentAlignment = Alignment.Center) { Text("Tertiary", color = content) }
                }
                Token(colors.tertiary, colors.onTertiary, "tertiary/onTertiary")
                Token(colors.tertiaryContainer, colors.onTertiaryContainer, "tertiaryContainer/onTertiaryContainer")
            }
        }
    }

    @Composable
    fun SurfaceContentSection() {
        val colors = MaterialTheme.colorScheme
        Section(
            title = "Surface 与内容",
            desc = listOf(
                "surface：常规组件容器背景（Card、AppBar 等）。",
                "onSurface：容器上的文本/图标色。",
                "surfaceVariant：次要容器背景/分隔元素。",
                "onSurfaceVariant：在次要容器上的文本/图标色。",
                "surfaceTint：用于 Elevated 组件的加色，体现海拔。",
            )
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Card { Box(modifier = Modifier.size(120.dp, 60.dp), contentAlignment = Alignment.Center) { Text("Surface", color = colors.onSurface) } }
                    var elevatedSurface by remember { mutableStateOf(true) }
                    LabeledSwitch("海拔", elevatedSurface) { elevatedSurface = it }
                    VerticalDivider(thickness = 1.dp)
                    ElevatedSurfaceSample(elevatedSurface)
                    Token(colors.surfaceVariant, colors.onSurfaceVariant, "surfaceVariant/onSurfaceVariant")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Token(colors.surfaceBright, colors.onSurface, "surfaceBright")
                    Token(colors.surfaceDim, colors.onSurface, "surfaceDim")
                }
            }
        }
    }

    @Composable
    fun BackgroundSection(textValue: String, onTextChange: (String) -> Unit) {
        val colors = MaterialTheme.colorScheme
        Section(
            title = "Background 与 onBackground",
            desc = listOf(
                "background：页面背景色（Scaffold 背景）。",
                "onBackground：页面背景上的文本/图标色。",
            )
        ) {
            var useContainer by remember { mutableStateOf(false) }
            var enabled by remember { mutableStateOf(true) }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                LabeledSwitch("启用输入", enabled) { enabled = it }
                LabeledSwitch("容器变体", useContainer) { useContainer = it }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                val bg = if (useContainer) colors.surfaceContainer else colors.background
                val fg = if (useContainer) colors.onSurface else colors.onBackground
                Surface(color = bg) {
                    Box(modifier = Modifier.size(220.dp, 80.dp), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Background", color = fg)
                            OutlinedTextField(value = textValue, onValueChange = onTextChange, enabled = enabled, label = { Text("输入") })
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun OutlineSection() {
        val colors = MaterialTheme.colorScheme
        Section(
            title = "Outline 与边框",
            desc = listOf(
                "outline：描边主色（Outlined 组件边框等）。",
                "outlineVariant：更弱的描边/分隔线（如 Divider）。",
                "输入光标（cursor）：默认为 primary；可通过 TextFieldDefaults.colors(cursorColor=...) 自定义。",
            )
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                var outlinedEnabled by remember { mutableStateOf(true) }
                LabeledSwitch("启用", outlinedEnabled) { outlinedEnabled = it }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(onClick = {}, enabled = outlinedEnabled) { Text("Outlined") }
                    OutlinedTextField(value = "", onValueChange = {}, label = { Text("Label") }, enabled = outlinedEnabled)
                    Token(colors.outline, colors.onSurface, "outline")
                    Token(colors.outlineVariant, colors.onSurface, "outlineVariant")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) { Token(colors.primary, colors.onPrimary, "cursor=primary") }
            }
        }
    }

    @Composable
    fun TextFieldStatesSection(textValue: String, onTextChange: (String) -> Unit) {
        val colors = MaterialTheme.colorScheme
        Section(
            title = "输入框状态",
            desc = listOf("错误态与辅助文本展示。")
        ) {
            var enabled by remember { mutableStateOf(true) }
            var isError by remember { mutableStateOf(false) }
            var showHelp by remember { mutableStateOf(false) }
            var showPlaceholder by remember { mutableStateOf(true) }
            var showCounter by remember { mutableStateOf(false) }
            var leading by remember { mutableStateOf(false) }
            var trailing by remember { mutableStateOf(false) }
            var showDisabledError by remember { mutableStateOf(false) }
            var source by remember { mutableStateOf(0) }
            var disabledPlaceholderVisible by remember { mutableStateOf(true) }
            var showFilledField by remember { mutableStateOf(true) }
            var showOutlinedField by remember { mutableStateOf(true) }
            val max = 20
            val accent = when (source) { 0 -> colors.primary; 1 -> colors.secondary; else -> colors.tertiary }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LabeledSwitch("启用", enabled) { enabled = it }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("错误", isError) { isError = it }
                    LabeledSwitch("帮助", showHelp) { showHelp = it }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("占位", showPlaceholder) { showPlaceholder = it }
                    LabeledSwitch("计数", showCounter) { showCounter = it }
                    LabeledSwitch("前缀", leading) { leading = it }
                    LabeledSwitch("后缀", trailing) { trailing = it }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = source == 0, onClick = { source = 0 })
                    Text("Primary")
                    RadioButton(selected = source == 1, onClick = { source = 1 })
                    Text("Secondary")
                    RadioButton(selected = source == 2, onClick = { source = 2 })
                    Text("Tertiary")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("禁用错误对比", showDisabledError) { showDisabledError = it }
                    LabeledSwitch("禁用占位可见", disabledPlaceholderVisible) { disabledPlaceholderVisible = it }
                    LabeledSwitch("填充型", showFilledField) { showFilledField = it }
                    LabeledSwitch("Outlined", showOutlinedField) { showOutlinedField = it }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    if (showOutlinedField) {
                        OutlinedTextField(
                            value = textValue,
                            onValueChange = { if (it.length <= max) onTextChange(it) },
                            label = { Text("Outlined") },
                            isError = isError,
                            enabled = enabled,
                            placeholder = { if (showPlaceholder && (enabled || disabledPlaceholderVisible)) Text("Placeholder") },
                            supportingText = {
                                val parts = mutableListOf<@Composable () -> Unit>()
                                if (showHelp) parts.add({ Text("辅助信息示例") })
                                if (showCounter) parts.add({ Text("${'$'}{textValue.length}/$max") })
                                if (parts.isNotEmpty()) Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { parts.forEach { it() } }
                            },
                            leadingIcon = { if (leading) Text("@") },
                            trailingIcon = { if (trailing) Text("#") },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = accent,
                                cursorColor = accent,
                                focusedLabelColor = accent,
                                errorIndicatorColor = colors.error,
                                errorCursorColor = colors.error,
                                errorLabelColor = colors.error,
                            )
                        )
                    }
                    if (showFilledField) {
                        TextField(
                            value = textValue,
                            onValueChange = { if (it.length <= max) onTextChange(it) },
                            label = { Text("Filled") },
                            isError = isError,
                            enabled = enabled,
                            placeholder = { if (showPlaceholder && (enabled || disabledPlaceholderVisible)) Text("Placeholder") },
                            supportingText = {
                                val parts = mutableListOf<@Composable () -> Unit>()
                                if (showHelp) parts.add({ Text("辅助信息示例") })
                                if (showCounter) parts.add({ Text("${'$'}{textValue.length}/$max") })
                                if (parts.isNotEmpty()) Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { parts.forEach { it() } }
                            },
                            leadingIcon = { if (leading) Text("@") },
                            trailingIcon = { if (trailing) Text("#") },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = accent,
                                cursorColor = accent,
                                focusedLabelColor = accent,
                                errorIndicatorColor = colors.error,
                                errorCursorColor = colors.error,
                                errorLabelColor = colors.error,
                            )
                        )
                    }
                    if (showDisabledError && showOutlinedField) {
                        OutlinedTextField(
                            value = textValue,
                            onValueChange = {},
                            label = { Text("禁用+错误") },
                            isError = true,
                            enabled = false,
                            placeholder = { if (showPlaceholder && disabledPlaceholderVisible) Text("Placeholder") },
                            supportingText = { if (showHelp) Text("辅助信息示例") },
                            leadingIcon = { if (leading) Text("@") },
                            trailingIcon = { if (trailing) Text("#") },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = accent,
                                cursorColor = accent,
                                focusedLabelColor = accent,
                                errorIndicatorColor = colors.error,
                                errorCursorColor = colors.error,
                                errorLabelColor = colors.error,
                            )
                        )
                    }
                    if (showDisabledError && showFilledField) {
                        TextField(
                            value = textValue,
                            onValueChange = {},
                            label = { Text("禁用+错误") },
                            isError = true,
                            enabled = false,
                            placeholder = { if (showPlaceholder && disabledPlaceholderVisible) Text("Placeholder") },
                            supportingText = { if (showHelp) Text("辅助信息示例") },
                            leadingIcon = { if (leading) Text("@") },
                            trailingIcon = { if (trailing) Text("#") },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = accent,
                                cursorColor = accent,
                                focusedLabelColor = accent,
                                errorIndicatorColor = colors.error,
                                errorCursorColor = colors.error,
                                errorLabelColor = colors.error,
                            )
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(color = colors.surfaceContainerLow, shape = RoundedCornerShape(12.dp)) {
                        Box(modifier = Modifier.padding(12.dp)) {
                            OutlinedTextField(
                                value = textValue,
                                onValueChange = { if (it.length <= max) onTextChange(it) },
                                label = { Text("Low 容器") },
                                enabled = enabled,
                                isError = isError,
                                placeholder = { if (showPlaceholder && (enabled || disabledPlaceholderVisible)) Text("Placeholder") },
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = accent,
                                    cursorColor = accent,
                                    focusedLabelColor = accent,
                                    errorIndicatorColor = colors.error,
                                    errorCursorColor = colors.error,
                                    errorLabelColor = colors.error,
                                )
                            )
                        }
                    }
                    Surface(color = colors.surfaceContainerHigh, shape = RoundedCornerShape(12.dp)) {
                        Box(modifier = Modifier.padding(12.dp)) {
                            OutlinedTextField(
                                value = textValue,
                                onValueChange = { if (it.length <= max) onTextChange(it) },
                                label = { Text("High 容器") },
                                enabled = enabled,
                                isError = isError,
                                placeholder = { if (showPlaceholder && (enabled || disabledPlaceholderVisible)) Text("Placeholder") },
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = accent,
                                    cursorColor = accent,
                                    focusedLabelColor = accent,
                                    errorIndicatorColor = colors.error,
                                    errorCursorColor = colors.error,
                                    errorLabelColor = colors.error,
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ErrorFeedbackSection() {
        val colors = MaterialTheme.colorScheme
        Section(
            title = "错误与反馈",
            desc = listOf(
                "error：错误/警示的强调色。",
                "onError：错误容器上的文本/图标色。",
                "errorContainer：错误容器背景（提示卡片等）。",
                "onErrorContainer：错误容器上的文本/图标色。",
            )
        ) {
            var enabled by remember { mutableStateOf(true) }
            var useContainer by remember { mutableStateOf(false) }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                LabeledSwitch("启用", enabled) { enabled = it }
                LabeledSwitch("容器变体", useContainer) { useContainer = it }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {}, enabled = enabled) { Text("正常") }
                Button(onClick = {}, enabled = enabled, colors = ButtonDefaults.buttonColors(containerColor = if (useContainer) colors.errorContainer else colors.error, contentColor = if (useContainer) colors.onErrorContainer else colors.onError)) { Text("错误") }
                Token(colors.error, colors.onError, "error/onError")
                Token(colors.errorContainer, colors.onErrorContainer, "errorContainer/onErrorContainer")
            }
        }
    }

    @Composable
    fun ProgressSection() {
        Section(
            title = "进度条",
            desc = listOf("确定/不确定：不确定不显示进度值，确定需提供 progress(0..1)。")
        ) {
            var determinate by remember { mutableStateOf(false) }
            var progress by remember { mutableStateOf(0.4f) }
            LabeledSwitch("确定", determinate) { determinate = it }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                if (determinate) {
                    LinearProgressIndicator(progress = { progress }, modifier = Modifier.size(160.dp, 6.dp))
                    Slider(value = progress, onValueChange = { progress = it }, valueRange = 0f..1f)
                } else {
                    LinearProgressIndicator(modifier = Modifier.size(160.dp, 6.dp))
                }
            }
        }
    }

    @Composable
    fun SelectionControlsSection() {
        Section(
            title = "选择控件",
            desc = listOf("默认选中态使用强调色（通常为 primary）。")
        ) {
            var enabledSel by remember { mutableStateOf(true) }
            var checked by remember { mutableStateOf(true) }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                LabeledSwitch("启用", enabledSel) { enabledSel = it }
                LabeledSwitch("选中", checked) { checked = it }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = checked, onCheckedChange = { checked = it }, enabled = enabledSel)
                Checkbox(checked = checked, onCheckedChange = { checked = it }, enabled = enabledSel)
                RadioButton(selected = checked, onClick = { checked = !checked }, enabled = enabledSel)
            }
        }
    }

    @Composable
    fun SnackbarInverseSection() {
        val colors = MaterialTheme.colorScheme
        val hostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        Section(
            title = "Snackbar 与 inverse",
            desc = listOf(
                "inverseSurface：反色表面（如 Snackbar 容器）。",
                "inverseOnSurface：反色表面上的文本/图标。",
                "inversePrimary：反色环境下的强调动作色（如 Snackbar 操作按钮）。",
            )
        ) {
            var enabled by remember { mutableStateOf(true) }
            var action by remember { mutableStateOf(false) }
            var top by remember { mutableStateOf(false) }
            var long by remember { mutableStateOf(false) }
            var indefinite by remember { mutableStateOf(false) }
            val duration = when {
                indefinite -> SnackbarDuration.Indefinite
                long -> SnackbarDuration.Long
                else -> SnackbarDuration.Short
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LabeledSwitch("启用", enabled) { enabled = it }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("Action", action) { action = it }
                    LabeledSwitch("顶部", top) { top = it }
                    LabeledSwitch("长时长", long) { long = it }
                    LabeledSwitch("无限", indefinite) { indefinite = it }
                    Button(onClick = { if (enabled) { scope.launch { hostState.showSnackbar(message = "这是一条消息", actionLabel = if (action) "OK" else null, duration = duration) } } }) { Text("显示 Snackbar") }
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = if (top) Alignment.TopCenter else Alignment.BottomCenter) { SnackbarHost(hostState = hostState) }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Token(colors.inverseSurface, colors.inverseOnSurface, "inverseSurface/inverseOnSurface")
                    Token(colors.inversePrimary, colors.onSurface, "inversePrimary")
                }
            }
        }
    }

    @Composable
    fun ScrimSection(showOverlay: Boolean, onShowOverlayChange: (Boolean) -> Unit) {
        val colors = MaterialTheme.colorScheme
        Section(
            title = "Scrim 与遮罩",
            desc = listOf("scrim：遮罩层色（模态弹窗/抽屉开启时的背景遮罩）。")
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                LabeledSwitch("显示遮罩", showOverlay) { onShowOverlayChange(it) }
                Token(colors.scrim, colors.onSurface, "scrim")
            }
        }
    }

    @Composable
    fun SurfaceContainersSection() {
        val colors = MaterialTheme.colorScheme
        Section(
            title = "Surface 容器层级",
            desc = listOf("surfaceContainer*：容器层级建议色；lowest→highest 表示海拔/层级递增。")
        ) {
            var high by remember { mutableStateOf(false) }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                LabeledSwitch("高层级", high) { high = it }
                val cont = if (high) colors.surfaceContainerHigh else colors.surfaceContainerLow
                val txt = colors.onSurface
                Surface(color = cont) { Box(modifier = Modifier.size(180.dp, 60.dp), contentAlignment = Alignment.Center) { Text(if (high) "High" else "Low", color = txt) } }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Token(colors.surfaceContainerLowest, colors.onSurface, "lowest")
                Token(colors.surfaceContainerLow, colors.onSurface, "low")
                Token(colors.surfaceContainer, colors.onSurface, "base")
                Token(colors.surfaceContainerHigh, colors.onSurface, "high")
                Token(colors.surfaceContainerHighest, colors.onSurface, "highest")
            }
        }
    }

    @Composable
    fun FixedColorsSection() {
        val colors = MaterialTheme.colorScheme
        Section(
            title = "Fixed 颜色展示",
            desc = listOf("*Fixed/*FixedDim：动态色（Android 12+）下的固定强度容器色及其内容色。")
        ) {
            var useFixed by remember { mutableStateOf(true) }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                LabeledSwitch("使用 Fixed", useFixed) { useFixed = it }
                val pCont = if (useFixed) colors.primaryFixed else colors.primary
                val pText = if (useFixed) colors.onPrimaryFixed else colors.onPrimary
                val pDimCont = if (useFixed) colors.primaryFixedDim else colors.primaryContainer
                val pDimText = if (useFixed) colors.onPrimaryFixedVariant else colors.onPrimaryContainer
                Token(pCont, pText, if (useFixed) "primaryFixed" else "primary")
                Token(pDimCont, pDimText, if (useFixed) "primaryFixedDim" else "primaryContainer")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val sCont = if (useFixed) colors.secondaryFixed else colors.secondary
                val sText = if (useFixed) colors.onSecondaryFixed else colors.onSecondary
                val sDimCont = if (useFixed) colors.secondaryFixedDim else colors.secondaryContainer
                val sDimText = if (useFixed) colors.onSecondaryFixedVariant else colors.onSecondaryContainer
                Token(sCont, sText, if (useFixed) "secondaryFixed" else "secondary")
                Token(sDimCont, sDimText, if (useFixed) "secondaryFixedDim" else "secondaryContainer")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val tCont = if (useFixed) colors.tertiaryFixed else colors.tertiary
                val tText = if (useFixed) colors.onTertiaryFixed else colors.onTertiary
                val tDimCont = if (useFixed) colors.tertiaryFixedDim else colors.tertiaryContainer
                val tDimText = if (useFixed) colors.onTertiaryFixedVariant else colors.onTertiaryContainer
                Token(tCont, tText, if (useFixed) "tertiaryFixed" else "tertiary")
                Token(tDimCont, tDimText, if (useFixed) "tertiaryFixedDim" else "tertiaryContainer")
            }
        }
    }
}
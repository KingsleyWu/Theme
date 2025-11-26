package com.just.theme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@Composable
@Preview
fun App() {
    MaterialTheme {
        var isEditor by remember { mutableStateOf(false) }
        val initialScheme = MaterialTheme.colorScheme
        var scheme by remember { mutableStateOf(initialScheme) }
        MaterialTheme(colorScheme = scheme) {
            if (isEditor) {
                ColorEditor(
                    scheme = scheme,
                    onSchemeChange = { scheme = it },
                    onBack = { isEditor = false },
                    onReset = { scheme = lightColorScheme() }
                )
            } else {
                ColorSchemeGallery(onEditClick = { isEditor = true })
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ColorSchemeGallery(onEditClick: (() -> Unit)? = null) {
    val colors = MaterialTheme.colorScheme
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var snackbarCounter by remember { mutableStateOf(0) }
    var showOverlay by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(true) }
    var enabledPrimary by remember { mutableStateOf(true) }
    var outlinedEnabled by remember { mutableStateOf(true) }
    var elevatedSurface by remember { mutableStateOf(true) }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("ColorScheme 示例") }, actions = {
                TextButton(onClick = { onEditClick?.invoke() }) { Text("编辑颜色") }
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
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
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("启用", enabledPrimary) { enabledPrimary = it }
                    Button(onClick = {}, enabled = enabledPrimary) { Text("Button") }
                    TextButton(onClick = {}, enabled = enabledPrimary) { Text("TextButton") }
                    Token(colors.primary, colors.onPrimary, "primary/onPrimary")
                    Token(colors.primaryContainer, colors.onPrimaryContainer, "primaryContainer/onPrimaryContainer")
                }
            }

            Section(
                title = "按钮形态对比",
                desc = listOf(
                    "Filled/Tonal/Outlined/Text 的并列比较。",
                )
            ) {
                var enabled by remember { mutableStateOf(true) }
                var source by remember { mutableStateOf(0) }
                var showFilled by remember { mutableStateOf(true) }
                var showTonal by remember { mutableStateOf(true) }
                var showOutlined by remember { mutableStateOf(true) }
                var showText by remember { mutableStateOf(true) }
                var showIcon by remember { mutableStateOf(false) }
                var radius by remember { mutableStateOf(12f) }
                var outline by remember { mutableStateOf(1f) }
                var iconContainer by remember { mutableStateOf(false) }
                var elevIndex by remember { mutableStateOf(0) }
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
                        LabeledSwitch("Icon", showIcon) { showIcon = it }
                        LabeledSwitch("Icon容器变体", iconContainer) { iconContainer = it }
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
                        Text("海拔")
                        Slider(value = elevIndex.toFloat(), onValueChange = { elevIndex = it.roundToInt().coerceIn(0, 3) }, valueRange = 0f..3f, steps = 3, modifier = Modifier.weight(1f))
                        val elevLabel = listOf(0, 2, 6, 8)[elevIndex]
                        Text("${elevLabel}dp")
                    }
                    val accent = when (source) {
                        0 -> colors.primary
                        1 -> colors.secondary
                        else -> colors.tertiary
                    }
                    val onAccent = when (source) {
                        0 -> colors.onPrimary
                        1 -> colors.onSecondary
                        else -> colors.onTertiary
                    }
                    val accentContainer = when (source) {
                        0 -> colors.primaryContainer
                        1 -> colors.secondaryContainer
                        else -> colors.tertiaryContainer
                    }
                    val onAccentContainer = when (source) {
                        0 -> colors.onPrimaryContainer
                        1 -> colors.onSecondaryContainer
                        else -> colors.onTertiaryContainer
                    }
                    val shape = RoundedCornerShape(radius.roundToInt().dp)
                    val elevDp = listOf(0.dp, 2.dp, 6.dp, 8.dp)[elevIndex]
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        if (showFilled) Button(onClick = {}, enabled = enabled, shape = shape, colors = ButtonDefaults.buttonColors(containerColor = accent, contentColor = onAccent), elevation = ButtonDefaults.buttonElevation(defaultElevation = elevDp)) { Text("Filled") }
                        if (showTonal) FilledTonalButton(onClick = {}, enabled = enabled, shape = shape, colors = ButtonDefaults.filledTonalButtonColors(containerColor = accentContainer, contentColor = onAccentContainer), elevation = ButtonDefaults.buttonElevation(defaultElevation = elevDp)) { Text("Tonal") }
                        if (showOutlined) OutlinedButton(onClick = {}, enabled = enabled, shape = shape, border = BorderStroke(outline.dp, accent), colors = ButtonDefaults.outlinedButtonColors(contentColor = accent)) { Text("Outlined") }
                        if (showText) TextButton(onClick = {}, enabled = enabled, shape = shape, colors = ButtonDefaults.textButtonColors(contentColor = accent)) { Text("Text") }
                        if (showIcon) {
                            if (iconContainer) {
                                Surface(color = accentContainer, shape = shape) {
                                    IconButton(onClick = {}, enabled = enabled) { Text("☆", color = onAccentContainer) }
                                }
                            } else {
                                IconButton(onClick = {}, enabled = enabled) { Text("☆", color = accent) }
                            }
                        }
                        Token(accent, onAccent, "accent")
                        Token(accentContainer, onAccentContainer, "accentContainer")
                    }
                }
            }

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
                    if (useContainer) {
                        FilledTonalButton(onClick = {}, enabled = enabled) { Text("FilledTonal") }
                    } else {
                        Button(
                            onClick = {},
                            enabled = enabled,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colors.secondary,
                                contentColor = colors.onSecondary
                            )
                        ) { Text("Secondary Button") }
                    }
                    FloatingActionButton(
                        onClick = {},
                        containerColor = if (useContainer) colors.secondaryContainer else colors.secondary,
                        contentColor = if (useContainer) colors.onSecondaryContainer else colors.onSecondary
                    ) { Text("+") }
                    Token(colors.secondary, colors.onSecondary, "secondary/onSecondary")
                    Token(colors.secondaryContainer, colors.onSecondaryContainer, "secondaryContainer/onSecondaryContainer")
                }
            }

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
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("容器变体", useContainer) { useContainer = it }
                    val container = if (useContainer) colors.tertiaryContainer else colors.tertiary
                    val content = if (useContainer) colors.onTertiaryContainer else colors.onTertiary
                    Card(colors = CardDefaults.cardColors(containerColor = container)) {
                        Box(modifier = Modifier.size(140.dp, 60.dp), contentAlignment = Alignment.Center) {
                            Text("Tertiary", color = content)
                        }
                    }
                    Token(colors.tertiary, colors.onTertiary, "tertiary/onTertiary")
                    Token(colors.tertiaryContainer, colors.onTertiaryContainer, "tertiaryContainer/onTertiaryContainer")
                }
            }

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
                        Card {
                            Box(modifier = Modifier.size(120.dp, 60.dp), contentAlignment = Alignment.Center) {
                                Text("Surface", color = colors.onSurface)
                            }
                        }
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
                    val bg = if (useContainer) colors.surfaceContainer else colors.background
                    val fg = if (useContainer) colors.onSurface else colors.onBackground
                    Surface(color = bg) {
                        Box(modifier = Modifier.size(220.dp, 80.dp), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Background", color = fg)
                                OutlinedTextField(value = textValue, onValueChange = { textValue = it }, enabled = enabled, label = { Text("输入") })
                            }
                        }
                    }
                }
            }

            Section(
                title = "Outline 与边框",
                desc = listOf(
                    "outline：描边主色（Outlined 组件边框等）。",
                    "outlineVariant：更弱的描边/分隔线（如 Divider）。",
                    "输入光标（cursor）：默认为 primary；可通过 TextFieldDefaults.colors(cursorColor=...) 自定义。",
                )
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        LabeledSwitch("启用", outlinedEnabled) { outlinedEnabled = it }
                        OutlinedButton(onClick = {}, enabled = outlinedEnabled) { Text("Outlined") }
                        OutlinedTextField(value = textValue, onValueChange = { textValue = it }, label = { Text("Label") }, enabled = outlinedEnabled)
                        Token(colors.outline, colors.onSurface, "outline")
                        Token(colors.outlineVariant, colors.onSurface, "outlineVariant")
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Token(colors.primary, colors.onPrimary, "cursor=primary")
                    }
                }
            }

            Section(
                title = "输入框状态",
                desc = listOf(
                    "错误态与辅助文本展示。",
                )
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
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        LabeledSwitch("启用", enabled) { enabled = it }
                        LabeledSwitch("错误", isError) { isError = it }
                        LabeledSwitch("帮助", showHelp) { showHelp = it }
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
                                onValueChange = { if (it.length <= max) textValue = it },
                                label = { Text("Outlined") },
                                isError = isError,
                                enabled = enabled,
                                placeholder = { if (showPlaceholder && (enabled || disabledPlaceholderVisible)) Text("Placeholder") },
                                supportingText = {
                                    val parts = mutableListOf<@Composable () -> Unit>()
                                    if (showHelp) parts.add({ Text("辅助信息示例") })
                                    if (showCounter) parts.add({ Text("${textValue.length}/$max") })
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
                                onValueChange = { if (it.length <= max) textValue = it },
                                label = { Text("Filled") },
                                isError = isError,
                                enabled = enabled,
                                placeholder = { if (showPlaceholder && (enabled || disabledPlaceholderVisible)) Text("Placeholder") },
                                supportingText = {
                                    val parts = mutableListOf<@Composable () -> Unit>()
                                    if (showHelp) parts.add({ Text("辅助信息示例") })
                                    if (showCounter) parts.add({ Text("${textValue.length}/$max") })
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
                                    onValueChange = { if (it.length <= max) textValue = it },
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
                                    onValueChange = { if (it.length <= max) textValue = it },
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
                    Button(onClick = {}, enabled = enabled) { Text("正常") }
                    Button(
                        onClick = {},
                        enabled = enabled,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (useContainer) colors.errorContainer else colors.error,
                            contentColor = if (useContainer) colors.onErrorContainer else colors.onError
                        )
                    ) { Text("错误") }
                    Token(colors.error, colors.onError, "error/onError")
                    Token(colors.errorContainer, colors.onErrorContainer, "errorContainer/onErrorContainer")
                }
            }
            Section(
                title = "进度条",
                desc = listOf(
                    "确定/不确定：不确定不显示进度值，确定需提供 progress(0..1)。",
                )
            ) {
                var determinate by remember { mutableStateOf(false) }
                var progress by remember { mutableStateOf(0.4f) }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("确定", determinate) { determinate = it }
                    if (determinate) {
                        LinearProgressIndicator(progress = { progress }, modifier = Modifier.size(160.dp, 6.dp))
                        Slider(value = progress, onValueChange = { progress = it }, valueRange = 0f..1f)
                    } else {
                        LinearProgressIndicator(modifier = Modifier.size(160.dp, 6.dp))
                    }
                }
            }

            Section(
                title = "选择控件",
                desc = listOf(
                    "默认选中态使用强调色（通常为 primary）。",
                )
            ) {
                var enabledSel by remember { mutableStateOf(true) }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("启用", enabledSel) { enabledSel = it }
                    LabeledSwitch("选中", checked) { checked = it }
                    Switch(checked = checked, onCheckedChange = { checked = it }, enabled = enabledSel)
                    Checkbox(checked = checked, onCheckedChange = { checked = it }, enabled = enabledSel)
                    RadioButton(selected = checked, onClick = { checked = !checked }, enabled = enabledSel)
                }
            }

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
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        LabeledSwitch("启用", enabled) { enabled = it }
                        LabeledSwitch("Action", action) { action = it }
                        LabeledSwitch("顶部", top) { top = it }
                        LabeledSwitch("长时长", long) { long = it }
                        LabeledSwitch("无限", indefinite) { indefinite = it }
                        Button(onClick = {
                            if (enabled) {
                                scope.launch {
                                    hostState.showSnackbar(
                                        message = "这是一条消息",
                                        actionLabel = if (action) "OK" else null,
                                        duration = duration
                                    )
                                }
                            }
                        }) { Text("显示 Snackbar") }
                    }
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = if (top) Alignment.TopCenter else Alignment.BottomCenter) {
                        SnackbarHost(hostState = hostState)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Token(colors.inverseSurface, colors.inverseOnSurface, "inverseSurface/inverseOnSurface")
                        Token(colors.inversePrimary, colors.onSurface, "inversePrimary")
                    }
                }
            }

            Section(
                title = "Scrim 与遮罩",
                desc = listOf(
                    "scrim：遮罩层色（模态弹窗/抽屉开启时的背景遮罩）。",
                )
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("显示遮罩", showOverlay) { showOverlay = it }
                    Token(colors.scrim, colors.onSurface, "scrim")
                }
            }

            Section(
                title = "Surface 容器层级",
                desc = listOf(
                    "surfaceContainer*：容器层级建议色；lowest→highest 表示海拔/层级递增。",
                )
            ) {
                var high by remember { mutableStateOf(false) }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LabeledSwitch("高层级", high) { high = it }
                    val cont = if (high) colors.surfaceContainerHigh else colors.surfaceContainerLow
                    val txt = colors.onSurface
                    Surface(color = cont) {
                        Box(modifier = Modifier.size(180.dp, 60.dp), contentAlignment = Alignment.Center) {
                            Text(if (high) "High" else "Low", color = txt)
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Token(colors.surfaceContainerLowest, colors.onSurface, "lowest")
                    Token(colors.surfaceContainerLow, colors.onSurface, "low")
                    Token(colors.surfaceContainer, colors.onSurface, "base")
                    Token(colors.surfaceContainerHigh, colors.onSurface, "high")
                    Token(colors.surfaceContainerHighest, colors.onSurface, "highest")
                }
            }

            Section(
                title = "Fixed 颜色展示",
                desc = listOf(
                    "*Fixed/*FixedDim：动态色（Android 12+）下的固定强度容器色及其内容色。",
                )
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
        if (showOverlay) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.scrim),
                contentAlignment = Alignment.Center
            ) {
                Card(shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("遮罩示例")
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(onClick = { showOverlay = false }) { Text("关闭") }
                        }
                    }
                }
            }
        }
        
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ColorEditor(
    scheme: ColorScheme,
    onSchemeChange: (ColorScheme) -> Unit,
    onBack: () -> Unit,
    onReset: () -> Unit,
) {
    val s = remember(scheme) { editorStateFromScheme(scheme) }
    var state by remember { mutableStateOf(s) }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("编辑颜色") }, actions = {
                TextButton(onClick = onReset) { Text("重置") }
                TextButton(onClick = onBack) { Text("返回示例") }
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Primary")
            ColorField("primary", state.primary) { state = state.copy(primary = it); onSchemeChange(buildScheme(state)) }
            ColorField("onPrimary", state.onPrimary) { state = state.copy(onPrimary = it); onSchemeChange(buildScheme(state)) }
            ColorField("primaryContainer", state.primaryContainer) { state = state.copy(primaryContainer = it); onSchemeChange(buildScheme(state)) }
            ColorField("onPrimaryContainer", state.onPrimaryContainer) { state = state.copy(onPrimaryContainer = it); onSchemeChange(buildScheme(state)) }
            ColorField("inversePrimary", state.inversePrimary) { state = state.copy(inversePrimary = it); onSchemeChange(buildScheme(state)) }

            SectionTitle("Secondary")
            ColorField("secondary", state.secondary) { state = state.copy(secondary = it); onSchemeChange(buildScheme(state)) }
            ColorField("onSecondary", state.onSecondary) { state = state.copy(onSecondary = it); onSchemeChange(buildScheme(state)) }
            ColorField("secondaryContainer", state.secondaryContainer) { state = state.copy(secondaryContainer = it); onSchemeChange(buildScheme(state)) }
            ColorField("onSecondaryContainer", state.onSecondaryContainer) { state = state.copy(onSecondaryContainer = it); onSchemeChange(buildScheme(state)) }

            SectionTitle("Tertiary")
            ColorField("tertiary", state.tertiary) { state = state.copy(tertiary = it); onSchemeChange(buildScheme(state)) }
            ColorField("onTertiary", state.onTertiary) { state = state.copy(onTertiary = it); onSchemeChange(buildScheme(state)) }
            ColorField("tertiaryContainer", state.tertiaryContainer) { state = state.copy(tertiaryContainer = it); onSchemeChange(buildScheme(state)) }
            ColorField("onTertiaryContainer", state.onTertiaryContainer) { state = state.copy(onTertiaryContainer = it); onSchemeChange(buildScheme(state)) }

            SectionTitle("Surface/Background")
            ColorField("background", state.background) { state = state.copy(background = it); onSchemeChange(buildScheme(state)) }
            ColorField("onBackground", state.onBackground) { state = state.copy(onBackground = it); onSchemeChange(buildScheme(state)) }
            ColorField("surface", state.surface) { state = state.copy(surface = it); onSchemeChange(buildScheme(state)) }
            ColorField("onSurface", state.onSurface) { state = state.copy(onSurface = it); onSchemeChange(buildScheme(state)) }
            ColorField("surfaceVariant", state.surfaceVariant) { state = state.copy(surfaceVariant = it); onSchemeChange(buildScheme(state)) }
            ColorField("onSurfaceVariant", state.onSurfaceVariant) { state = state.copy(onSurfaceVariant = it); onSchemeChange(buildScheme(state)) }
            ColorField("surfaceTint", state.surfaceTint) { state = state.copy(surfaceTint = it); onSchemeChange(buildScheme(state)) }
            ColorField("inverseSurface", state.inverseSurface) { state = state.copy(inverseSurface = it); onSchemeChange(buildScheme(state)) }
            ColorField("inverseOnSurface", state.inverseOnSurface) { state = state.copy(inverseOnSurface = it); onSchemeChange(buildScheme(state)) }

            SectionTitle("Error/Outline/Scrim")
            ColorField("error", state.error) { state = state.copy(error = it); onSchemeChange(buildScheme(state)) }
            ColorField("onError", state.onError) { state = state.copy(onError = it); onSchemeChange(buildScheme(state)) }
            ColorField("errorContainer", state.errorContainer) { state = state.copy(errorContainer = it); onSchemeChange(buildScheme(state)) }
            ColorField("onErrorContainer", state.onErrorContainer) { state = state.copy(onErrorContainer = it); onSchemeChange(buildScheme(state)) }
            ColorField("outline", state.outline) { state = state.copy(outline = it); onSchemeChange(buildScheme(state)) }
            ColorField("outlineVariant", state.outlineVariant) { state = state.copy(outlineVariant = it); onSchemeChange(buildScheme(state)) }
            ColorField("scrim", state.scrim) { state = state.copy(scrim = it); onSchemeChange(buildScheme(state)) }

            SectionTitle("Surface 容器层级")
            ColorField("surfaceBright", state.surfaceBright) { state = state.copy(surfaceBright = it); onSchemeChange(buildScheme(state)) }
            ColorField("surfaceDim", state.surfaceDim) { state = state.copy(surfaceDim = it); onSchemeChange(buildScheme(state)) }
            ColorField("surfaceContainerLowest", state.surfaceContainerLowest) { state = state.copy(surfaceContainerLowest = it); onSchemeChange(buildScheme(state)) }
            ColorField("surfaceContainerLow", state.surfaceContainerLow) { state = state.copy(surfaceContainerLow = it); onSchemeChange(buildScheme(state)) }
            ColorField("surfaceContainer", state.surfaceContainer) { state = state.copy(surfaceContainer = it); onSchemeChange(buildScheme(state)) }
            ColorField("surfaceContainerHigh", state.surfaceContainerHigh) { state = state.copy(surfaceContainerHigh = it); onSchemeChange(buildScheme(state)) }
            ColorField("surfaceContainerHighest", state.surfaceContainerHighest) { state = state.copy(surfaceContainerHighest = it); onSchemeChange(buildScheme(state)) }

            SectionTitle("Fixed 家族")
            ColorField("primaryFixed", state.primaryFixed) { state = state.copy(primaryFixed = it); onSchemeChange(buildScheme(state)) }
            ColorField("primaryFixedDim", state.primaryFixedDim) { state = state.copy(primaryFixedDim = it); onSchemeChange(buildScheme(state)) }
            ColorField("onPrimaryFixed", state.onPrimaryFixed) { state = state.copy(onPrimaryFixed = it); onSchemeChange(buildScheme(state)) }
            ColorField("onPrimaryFixedVariant", state.onPrimaryFixedVariant) { state = state.copy(onPrimaryFixedVariant = it); onSchemeChange(buildScheme(state)) }
            ColorField("secondaryFixed", state.secondaryFixed) { state = state.copy(secondaryFixed = it); onSchemeChange(buildScheme(state)) }
            ColorField("secondaryFixedDim", state.secondaryFixedDim) { state = state.copy(secondaryFixedDim = it); onSchemeChange(buildScheme(state)) }
            ColorField("onSecondaryFixed", state.onSecondaryFixed) { state = state.copy(onSecondaryFixed = it); onSchemeChange(buildScheme(state)) }
            ColorField("onSecondaryFixedVariant", state.onSecondaryFixedVariant) { state = state.copy(onSecondaryFixedVariant = it); onSchemeChange(buildScheme(state)) }
            ColorField("tertiaryFixed", state.tertiaryFixed) { state = state.copy(tertiaryFixed = it); onSchemeChange(buildScheme(state)) }
            ColorField("tertiaryFixedDim", state.tertiaryFixedDim) { state = state.copy(tertiaryFixedDim = it); onSchemeChange(buildScheme(state)) }
            ColorField("onTertiaryFixed", state.onTertiaryFixed) { state = state.copy(onTertiaryFixed = it); onSchemeChange(buildScheme(state)) }
            ColorField("onTertiaryFixedVariant", state.onTertiaryFixedVariant) { state = state.copy(onTertiaryFixedVariant = it); onSchemeChange(buildScheme(state)) }
        }
    }
}

data class EditableScheme(
    val primary: String,
    val onPrimary: String,
    val primaryContainer: String,
    val onPrimaryContainer: String,
    val inversePrimary: String,
    val secondary: String,
    val onSecondary: String,
    val secondaryContainer: String,
    val onSecondaryContainer: String,
    val tertiary: String,
    val onTertiary: String,
    val tertiaryContainer: String,
    val onTertiaryContainer: String,
    val background: String,
    val onBackground: String,
    val surface: String,
    val onSurface: String,
    val surfaceVariant: String,
    val onSurfaceVariant: String,
    val surfaceTint: String,
    val inverseSurface: String,
    val inverseOnSurface: String,
    val error: String,
    val onError: String,
    val errorContainer: String,
    val onErrorContainer: String,
    val outline: String,
    val outlineVariant: String,
    val scrim: String,
    val surfaceBright: String,
    val surfaceDim: String,
    val surfaceContainer: String,
    val surfaceContainerHigh: String,
    val surfaceContainerHighest: String,
    val surfaceContainerLow: String,
    val surfaceContainerLowest: String,
    val primaryFixed: String,
    val primaryFixedDim: String,
    val onPrimaryFixed: String,
    val onPrimaryFixedVariant: String,
    val secondaryFixed: String,
    val secondaryFixedDim: String,
    val onSecondaryFixed: String,
    val onSecondaryFixedVariant: String,
    val tertiaryFixed: String,
    val tertiaryFixedDim: String,
    val onTertiaryFixed: String,
    val onTertiaryFixedVariant: String,
)

fun editorStateFromScheme(s: ColorScheme): EditableScheme = EditableScheme(
    primary = colorToHex(s.primary),
    onPrimary = colorToHex(s.onPrimary),
    primaryContainer = colorToHex(s.primaryContainer),
    onPrimaryContainer = colorToHex(s.onPrimaryContainer),
    inversePrimary = colorToHex(s.inversePrimary),
    secondary = colorToHex(s.secondary),
    onSecondary = colorToHex(s.onSecondary),
    secondaryContainer = colorToHex(s.secondaryContainer),
    onSecondaryContainer = colorToHex(s.onSecondaryContainer),
    tertiary = colorToHex(s.tertiary),
    onTertiary = colorToHex(s.onTertiary),
    tertiaryContainer = colorToHex(s.tertiaryContainer),
    onTertiaryContainer = colorToHex(s.onTertiaryContainer),
    background = colorToHex(s.background),
    onBackground = colorToHex(s.onBackground),
    surface = colorToHex(s.surface),
    onSurface = colorToHex(s.onSurface),
    surfaceVariant = colorToHex(s.surfaceVariant),
    onSurfaceVariant = colorToHex(s.onSurfaceVariant),
    surfaceTint = colorToHex(s.surfaceTint),
    inverseSurface = colorToHex(s.inverseSurface),
    inverseOnSurface = colorToHex(s.inverseOnSurface),
    error = colorToHex(s.error),
    onError = colorToHex(s.onError),
    errorContainer = colorToHex(s.errorContainer),
    onErrorContainer = colorToHex(s.onErrorContainer),
    outline = colorToHex(s.outline),
    outlineVariant = colorToHex(s.outlineVariant),
    scrim = colorToHex(s.scrim),
    surfaceBright = colorToHex(s.surfaceBright),
    surfaceDim = colorToHex(s.surfaceDim),
    surfaceContainer = colorToHex(s.surfaceContainer),
    surfaceContainerHigh = colorToHex(s.surfaceContainerHigh),
    surfaceContainerHighest = colorToHex(s.surfaceContainerHighest),
    surfaceContainerLow = colorToHex(s.surfaceContainerLow),
    surfaceContainerLowest = colorToHex(s.surfaceContainerLowest),
    primaryFixed = colorToHex(s.primaryFixed),
    primaryFixedDim = colorToHex(s.primaryFixedDim),
    onPrimaryFixed = colorToHex(s.onPrimaryFixed),
    onPrimaryFixedVariant = colorToHex(s.onPrimaryFixedVariant),
    secondaryFixed = colorToHex(s.secondaryFixed),
    secondaryFixedDim = colorToHex(s.secondaryFixedDim),
    onSecondaryFixed = colorToHex(s.onSecondaryFixed),
    onSecondaryFixedVariant = colorToHex(s.onSecondaryFixedVariant),
    tertiaryFixed = colorToHex(s.tertiaryFixed),
    tertiaryFixedDim = colorToHex(s.tertiaryFixedDim),
    onTertiaryFixed = colorToHex(s.onTertiaryFixed),
    onTertiaryFixedVariant = colorToHex(s.onTertiaryFixedVariant),
)

fun buildScheme(s: EditableScheme): ColorScheme = ColorScheme(
    primary = parseHexColor(s.primary) ?: Color(0xFF6200EE),
    onPrimary = parseHexColor(s.onPrimary) ?: Color(0xFFFFFFFF),
    primaryContainer = parseHexColor(s.primaryContainer) ?: parseHexColor(s.primary) ?: Color(0xFF6200EE),
    onPrimaryContainer = parseHexColor(s.onPrimaryContainer) ?: Color(0xFF1C1B1F),
    inversePrimary = parseHexColor(s.inversePrimary) ?: parseHexColor(s.primary) ?: Color(0xFF6200EE),
    secondary = parseHexColor(s.secondary) ?: Color(0xFF03DAC6),
    onSecondary = parseHexColor(s.onSecondary) ?: Color(0xFF000000),
    secondaryContainer = parseHexColor(s.secondaryContainer) ?: parseHexColor(s.secondary) ?: Color(0xFF03DAC6),
    onSecondaryContainer = parseHexColor(s.onSecondaryContainer) ?: Color(0xFF1C1B1F),
    tertiary = parseHexColor(s.tertiary) ?: Color(0xFF018786),
    onTertiary = parseHexColor(s.onTertiary) ?: Color(0xFFFFFFFF),
    tertiaryContainer = parseHexColor(s.tertiaryContainer) ?: parseHexColor(s.tertiary) ?: Color(0xFF018786),
    onTertiaryContainer = parseHexColor(s.onTertiaryContainer) ?: Color(0xFF1C1B1F),
    background = parseHexColor(s.background) ?: Color(0xFFFFFBFE),
    onBackground = parseHexColor(s.onBackground) ?: Color(0xFF1C1B1F),
    surface = parseHexColor(s.surface) ?: Color(0xFFFFFBFE),
    onSurface = parseHexColor(s.onSurface) ?: Color(0xFF1C1B1F),
    surfaceVariant = parseHexColor(s.surfaceVariant) ?: Color(0xFFE7E0EC),
    onSurfaceVariant = parseHexColor(s.onSurfaceVariant) ?: Color(0xFF49454F),
    surfaceTint = parseHexColor(s.surfaceTint) ?: parseHexColor(s.primary) ?: Color(0xFF6200EE),
    inverseSurface = parseHexColor(s.inverseSurface) ?: Color(0xFF313033),
    inverseOnSurface = parseHexColor(s.inverseOnSurface) ?: Color(0xFFF4EFF4),
    error = parseHexColor(s.error) ?: Color(0xFFB00020),
    onError = parseHexColor(s.onError) ?: Color(0xFFFFFFFF),
    errorContainer = parseHexColor(s.errorContainer) ?: Color(0xFFFCEEEE),
    onErrorContainer = parseHexColor(s.onErrorContainer) ?: Color(0xFF410E0B),
    outline = parseHexColor(s.outline) ?: Color(0xFF79747E),
    outlineVariant = parseHexColor(s.outlineVariant) ?: Color(0xFFCAC4D0),
    scrim = parseHexColor(s.scrim) ?: Color(0xFF000000),
    surfaceBright = parseHexColor(s.surfaceBright) ?: Color(0xFFFDF7FF),
    surfaceDim = parseHexColor(s.surfaceDim) ?: Color(0xFFDED8E1),
    surfaceContainer = parseHexColor(s.surfaceContainer) ?: Color(0xFFF3EDF7),
    surfaceContainerHigh = parseHexColor(s.surfaceContainerHigh) ?: Color(0xFFE9E3EC),
    surfaceContainerHighest = parseHexColor(s.surfaceContainerHighest) ?: Color(0xFFE1DAE3),
    surfaceContainerLow = parseHexColor(s.surfaceContainerLow) ?: Color(0xFFF7F2FA),
    surfaceContainerLowest = parseHexColor(s.surfaceContainerLowest) ?: Color(0xFFFEF7FF),
    primaryFixed = parseHexColor(s.primaryFixed) ?: parseHexColor(s.primary) ?: Color(0xFF6200EE),
    primaryFixedDim = parseHexColor(s.primaryFixedDim) ?: parseHexColor(s.primary) ?: Color(0xFF4F00C8),
    onPrimaryFixed = parseHexColor(s.onPrimaryFixed) ?: Color(0xFFFFFFFF),
    onPrimaryFixedVariant = parseHexColor(s.onPrimaryFixedVariant) ?: Color(0xFF1C1B1F),
    secondaryFixed = parseHexColor(s.secondaryFixed) ?: parseHexColor(s.secondary) ?: Color(0xFF03DAC6),
    secondaryFixedDim = parseHexColor(s.secondaryFixedDim) ?: parseHexColor(s.secondary) ?: Color(0xFF02B3A6),
    onSecondaryFixed = parseHexColor(s.onSecondaryFixed) ?: Color(0xFF000000),
    onSecondaryFixedVariant = parseHexColor(s.onSecondaryFixedVariant) ?: Color(0xFF1C1B1F),
    tertiaryFixed = parseHexColor(s.tertiaryFixed) ?: parseHexColor(s.tertiary) ?: Color(0xFF018786),
    tertiaryFixedDim = parseHexColor(s.tertiaryFixedDim) ?: parseHexColor(s.tertiary) ?: Color(0xFF016C6D),
    onTertiaryFixed = parseHexColor(s.onTertiaryFixed) ?: Color(0xFFFFFFFF),
    onTertiaryFixedVariant = parseHexColor(s.onTertiaryFixedVariant) ?: Color(0xFF1C1B1F),
)

@Composable
fun ColorField(label: String, value: String, onChange: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(value = value, onValueChange = onChange, label = { Text(label) })
        val preview = parseHexColor(value) ?: MaterialTheme.colorScheme.surfaceVariant
        Surface(color = preview, shape = RoundedCornerShape(8.dp)) {
            Box(modifier = Modifier.size(56.dp, 32.dp)) {}
        }
        var pickerOpen by remember { mutableStateOf(false) }
        TextButton(onClick = { pickerOpen = true }) { Text("拾色器") }
        if (pickerOpen) {
            ColorPickerDialog(
                initialColor = preview,
                onConfirm = { c ->
                    onChange(colorToHex(c))
                    pickerOpen = false
                },
                onDismiss = { pickerOpen = false }
            )
        }
    }
}

@Composable
fun ColorPickerDialog(
    initialColor: Color,
    onConfirm: (Color) -> Unit,
    onDismiss: () -> Unit,
) {
    var r by remember(initialColor) { mutableStateOf((initialColor.red * 255).roundToInt().coerceIn(0, 255)) }
    var g by remember(initialColor) { mutableStateOf((initialColor.green * 255).roundToInt().coerceIn(0, 255)) }
    var b by remember(initialColor) { mutableStateOf((initialColor.blue * 255).roundToInt().coerceIn(0, 255)) }
    var a by remember(initialColor) { mutableStateOf((initialColor.alpha * 255).roundToInt().coerceIn(0, 255)) }
    val current = Color(r / 255f, g / 255f, b / 255f, a / 255f)
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("选择颜色") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(color = current, shape = RoundedCornerShape(8.dp)) {
                    Box(modifier = Modifier.size(120.dp, 48.dp)) {}
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("R")
                    Slider(value = r.toFloat(), onValueChange = { r = it.roundToInt().coerceIn(0, 255) }, valueRange = 0f..255f)
                    Text(r.toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("G")
                    Slider(value = g.toFloat(), onValueChange = { g = it.roundToInt().coerceIn(0, 255) }, valueRange = 0f..255f)
                    Text(g.toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("B")
                    Slider(value = b.toFloat(), onValueChange = { b = it.roundToInt().coerceIn(0, 255) }, valueRange = 0f..255f)
                    Text(b.toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("A")
                    Slider(value = a.toFloat(), onValueChange = { a = it.roundToInt().coerceIn(0, 255) }, valueRange = 0f..255f)
                    Text(a.toString())
                }
                Text(colorToHex(current))
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(current) }) { Text("确定") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("取消") }
        }
    )
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

@Composable
fun SectionTitle(title: String) {
    Text(text = title, fontWeight = FontWeight.Bold)
}

@Composable
fun Section(title: String, desc: List<String>, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.weight(1f)) { SectionTitle(title) }
            TextButton(onClick = { expanded = !expanded }) { Text(if (expanded) "收起说明" else "展开说明") }
        }
        AnimatedVisibility(expanded) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                desc.forEach { Text(it) }
            }
        }
        content()
    }
}

@Composable
fun LabeledSwitch(text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun Token(bg: Color, fg: Color, label: String) {
    Surface(color = bg, shape = RoundedCornerShape(12.dp)) {
        Box(modifier = Modifier.padding(8.dp).wrapContentSize(), contentAlignment = Alignment.Center) {
            Text(label, color = fg)
        }
    }
}

@Composable
fun ElevatedSurfaceSample(elevated: Boolean) {
    val colors = MaterialTheme.colorScheme
    val cardColors = if (elevated) CardDefaults.elevatedCardColors() else CardDefaults.cardColors()
    Card(colors = cardColors) {
        Box(modifier = Modifier.padding(8.dp).wrapContentSize(), contentAlignment = Alignment.Center) {
            Text(if (elevated) "Elevated" else "Flat", color = colors.onSurface)
        }
    }
}
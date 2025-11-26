package com.just.theme

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
import androidx.compose.ui.unit.dp
import com.just.theme.sections.ControlSections
import com.just.theme.ui.SectionTitle
import com.just.theme.util.colorToHex
import com.just.theme.util.parseHexColor
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
    var showOverlay by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf("") }
    var iconTypeGlobal by remember { mutableStateOf(0) }
    var iconSizeGlobal by remember { mutableStateOf(24f) }
    var iconPaddingGlobal by remember { mutableStateOf(8f) }
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
            ControlSections.PrimaryFamilySection()

            ControlSections.ButtonsSection(
                iconTypeGlobal = iconTypeGlobal,
                iconSizeGlobal = iconSizeGlobal,
                iconPaddingGlobal = iconPaddingGlobal,
                onIconTypeChange = { iconTypeGlobal = it },
                onIconSizeChange = { iconSizeGlobal = it },
                onIconPaddingChange = { iconPaddingGlobal = it },
            )

            ControlSections.InteractionSection(
                iconTypeGlobal = iconTypeGlobal,
                iconSizeGlobal = iconSizeGlobal,
                iconPaddingGlobal = iconPaddingGlobal,
                onIconSizeChange = { iconSizeGlobal = it },
                onIconPaddingChange = { iconPaddingGlobal = it },
            )

            ControlSections.SecondaryFamilySection()

            ControlSections.TertiaryFamilySection()

            ControlSections.SurfaceContentSection()

            ControlSections.BackgroundSection(textValue = textValue, onTextChange = { textValue = it })

            ControlSections.OutlineSection()

            ControlSections.TextFieldStatesSection(textValue = textValue, onTextChange = { textValue = it })

            ControlSections.ErrorFeedbackSection()
            ControlSections.ProgressSection()

            ControlSections.SelectionControlsSection()

            ControlSections.SnackbarInverseSection()

            ControlSections.ScrimSection(showOverlay = showOverlay, onShowOverlayChange = { showOverlay = it })

            ControlSections.SurfaceContainersSection()

            ControlSections.FixedColorsSection()
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
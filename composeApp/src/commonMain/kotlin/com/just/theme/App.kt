package com.just.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        ColorSchemeGallery()
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ColorSchemeGallery() {
    val colors = MaterialTheme.colorScheme
    val hostState = remember { SnackbarHostState() }
    var snackbarCounter by remember { mutableStateOf(0) }
    var showOverlay by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(true) }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("ColorScheme 示例") })
        },
        snackbarHost = { SnackbarHost(hostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                SectionTitle("Primary 家族")
                Text("primary：主色。用于高强调交互控件容器（如 Button、选中态）。")
                Text("onPrimary：主色容器上的文本/图标色，保证对比度。")
                Text("primaryContainer：主色容器背景，适合卡片或大面积强调背景。")
                Text("onPrimaryContainer：主色容器上的文本/图标色。")
                Text("inversePrimary：反色环境下的强调动作色（例如 Snackbar 的 action）。")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = {}) { Text("Button") }
                TextButton(onClick = {}) { Text("TextButton") }
                Token(colors.primary, colors.onPrimary, "primary/onPrimary")
                Token(colors.primaryContainer, colors.onPrimaryContainer, "primaryContainer/onPrimaryContainer")
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                SectionTitle("Secondary 家族")
                Text("secondary：次强调色。用于次要交互或选中态的容器色。")
                Text("onSecondary：次强调容器上的文本/图标色。")
                Text("secondaryContainer：次强调容器背景（如 FilledTonalButton）。")
                Text("onSecondaryContainer：次强调容器上的文本/图标色。")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilledTonalButton(onClick = {}) { Text("FilledTonal") }
                FloatingActionButton(onClick = {}) { Text("+") }
                Token(colors.secondary, colors.onSecondary, "secondary/onSecondary")
                Token(colors.secondaryContainer, colors.onSecondaryContainer, "secondaryContainer/onSecondaryContainer")
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                SectionTitle("Tertiary 家族")
                Text("tertiary：第三强调色。用于差异化点缀或分类的强调。")
                Text("onTertiary：第三强调容器上的文本/图标色。")
                Text("tertiaryContainer：第三强调容器背景。")
                Text("onTertiaryContainer：第三强调容器上的文本/图标色。")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Token(colors.tertiary, colors.onTertiary, "tertiary/onTertiary")
                Token(colors.tertiaryContainer, colors.onTertiaryContainer, "tertiaryContainer/onTertiaryContainer")
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                SectionTitle("Surface 与内容")
                Text("surface：常规组件容器背景（Card、AppBar 等）。")
                Text("onSurface：容器上的文本/图标色。")
                Text("surfaceVariant：次要容器背景/分隔元素。")
                Text("onSurfaceVariant：在次要容器上的文本/图标色。")
                Text("surfaceTint：用于 Elevated 组件的加色，体现海拔。")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card {
                    Box(modifier = Modifier.size(120.dp, 60.dp), contentAlignment = Alignment.Center) {
                        Text("Surface", color = colors.onSurface)
                    }
                }
                VerticalDivider(thickness = 1.dp)
                ElevatedSurfaceSample()
                Token(colors.surfaceVariant, colors.onSurfaceVariant, "surfaceVariant/onSurfaceVariant")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Token(colors.surfaceBright, colors.onSurface, "surfaceBright")
                Token(colors.surfaceDim, colors.onSurface, "surfaceDim")
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                SectionTitle("Background 与 onBackground")
                Text("background：页面背景色（Scaffold 背景）。")
                Text("onBackground：页面背景上的文本/图标色。")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(color = colors.background) {
                    Box(modifier = Modifier.size(160.dp, 60.dp), contentAlignment = Alignment.Center) {
                        Text("Background", color = colors.onBackground)
                    }
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                SectionTitle("Outline 与边框")
                Text("outline：描边主色（Outlined 组件边框等）。")
                Text("outlineVariant：更弱的描边/分隔线（如 Divider）。")
                Text("输入光标（cursor）：默认为 primary；可通过 TextFieldDefaults.colors(cursorColor=...) 自定义。")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = {}) { Text("Outlined") }
                OutlinedTextField(value = textValue, onValueChange = { textValue = it }, label = { Text("Label") })
                Token(colors.outline, colors.onSurface, "outline")
                Token(colors.outlineVariant, colors.onSurface, "outlineVariant")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Token(colors.primary, colors.onPrimary, "cursor=primary")
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                SectionTitle("错误与反馈")
                Text("error：错误/警示的强调色。")
                Text("onError：错误容器上的文本/图标色。")
                Text("errorContainer：错误容器背景（提示卡片等）。")
                Text("onErrorContainer：错误容器上的文本/图标色。")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = {}) { Text("正常") }
                Button(onClick = {}) { Text("错误") }
                Token(colors.error, colors.onError, "error/onError")
                Token(colors.errorContainer, colors.onErrorContainer, "errorContainer/onErrorContainer")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                LinearProgressIndicator(modifier = Modifier.size(160.dp, 6.dp))
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                SectionTitle("选择控件")
                Text("默认选中态使用强调色（通常为 primary）。")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Switch(checked = checked, onCheckedChange = { checked = it })
                Checkbox(checked = checked, onCheckedChange = { checked = it })
                RadioButton(selected = checked, onClick = { checked = !checked })
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                SectionTitle("Snackbar 与 inverse")
                Text("inverseSurface：反色表面（如 Snackbar 容器）。")
                Text("inverseOnSurface：反色表面上的文本/图标。")
                Text("inversePrimary：反色环境下的强调动作色（如 Snackbar 操作按钮）。")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = { snackbarCounter++ }) { Text("显示 Snackbar") }
                Token(colors.inverseSurface, colors.inverseOnSurface, "inverseSurface/inverseOnSurface")
                Token(colors.inversePrimary, colors.onSurface, "inversePrimary")
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                SectionTitle("Scrim 与遮罩")
                Text("scrim：遮罩层色（模态弹窗/抽屉开启时的背景遮罩）。")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = { showOverlay = true }) { Text("显示遮罩") }
                Token(colors.scrim, colors.onSurface, "scrim")
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                SectionTitle("Surface 容器层级")
                Text("surfaceContainer*：容器层级建议色；lowest→highest 表示海拔/层级递增。")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Token(colors.surfaceContainerLowest, colors.onSurface, "lowest")
                Token(colors.surfaceContainerLow, colors.onSurface, "low")
                Token(colors.surfaceContainer, colors.onSurface, "base")
                Token(colors.surfaceContainerHigh, colors.onSurface, "high")
                Token(colors.surfaceContainerHighest, colors.onSurface, "highest")
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                SectionTitle("Fixed 颜色展示")
                Text("primaryFixed/Dim：动态色（Android 12+）下的主色固定强度容器。")
                Text("onPrimaryFixed/Variant：对应容器上的文本/图标色。")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Token(colors.primaryFixed, colors.onPrimaryFixed, "primaryFixed/onPrimaryFixed")
                Token(colors.primaryFixedDim, colors.onPrimaryFixedVariant, "primaryFixedDim/onPrimaryFixedVariant")
            }
            Text("secondaryFixed/Dim：动态色下的次强调固定强度容器；onSecondaryFixed/Variant 为其内容色。")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Token(colors.secondaryFixed, colors.onSecondaryFixed, "secondaryFixed/onSecondaryFixed")
                Token(colors.secondaryFixedDim, colors.onSecondaryFixedVariant, "secondaryFixedDim/onSecondaryFixedVariant")
            }
            Text("tertiaryFixed/Dim：动态色下的第三强调固定强度容器；onTertiaryFixed/Variant 为其内容色。")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Token(colors.tertiaryFixed, colors.onTertiaryFixed, "tertiaryFixed/onTertiaryFixed")
                Token(colors.tertiaryFixedDim, colors.onTertiaryFixedVariant, "tertiaryFixedDim/onTertiaryFixedVariant")
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
        LaunchedEffect(snackbarCounter) {
            if (snackbarCounter > 0) {
                hostState.showSnackbar("这是一条消息")
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(text = title, fontWeight = FontWeight.Bold)
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
fun ElevatedSurfaceSample() {
    val colors = MaterialTheme.colorScheme
    Card(colors = CardDefaults.elevatedCardColors()) {
        Box(modifier = Modifier.padding(8.dp).wrapContentSize(), contentAlignment = Alignment.Center) {
            Text("Elevated", color = colors.onSurface)
        }
    }
}
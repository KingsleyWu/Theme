# Theme — Compose Multiplatform Material3 ColorScheme 学习与演示

Theme 是一个基于 Compose Multiplatform（Android/iOS/Web/Desktop）的 Material3 `ColorScheme` 学习与演示项目。
它提供完整的 UI 示例来展示每个颜色角色的使用位置，并内置颜色编辑器与拾色器，帮助你快速理解与定制主题配色。

## 特性

- ColorScheme 示例页：逐组展示 `primary/secondary/tertiary`、`surface/background`、`outline`、`error`、`inverse`、`scrim` 等角色的典型用法，并配有中文说明。
- 容器层级：演示 `surfaceContainer*` 与 `surfaceBright/surfaceDim` 的层级建议色。
- 动态色固定角色：演示 `*Fixed/*FixedDim` 及对应 `on*` 的展示区块。
- 颜色编辑页：可直接编辑 `ColorScheme` 的所有字段（十六进制 `#RRGGBB`/`#AARRGGBB`），即时应用到主题。
- 拾色器弹窗：RGBA 滑条与预览联动，一键写回字段并同步主题与示例页。

## 运行

- Android（Windows）：
  - `.gradlew.bat :composeApp:assembleDebug`
- Desktop (JVM)：
  - `.gradlew.bat :composeApp:run`
- Web（Wasm）：
  - `.gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun`
- Web（JS）：
  - `.gradlew.bat :composeApp:jsBrowserDevelopmentRun`
- iOS：
  - 在 IDE 的运行配置中使用 iOS 目标，或打开 [/iosApp](./iosApp) 目录到 Xcode 运行。

## 使用说明

- 打开应用进入“ColorScheme 示例”页，右上角点击“编辑颜色”进入编辑页。
- 在编辑页：
  - 修改任意字段的十六进制颜色（支持 `#RRGGBB`/`#AARRGGBB`），主题即时更新。
  - 点击字段右侧“拾色器”，使用 RGBA 滑条与预览选择颜色，点击“确定”写回该字段。
  - 顶部“重置”按钮恢复默认 `lightColorScheme()`；“返回示例”切换回示例页查看联动效果。
- 文本输入光标（cursor）默认使用 `primary`；在“Outline 与边框”区域有专门标注与示例。

## 关键文件

- 应用入口：`composeApp/src/commonMain/kotlin/com/just/theme/App.kt:64-81`
- 示例页：`composeApp/src/commonMain/kotlin/com/just/theme/App.kt:86-305`
- 颜色编辑页：`composeApp/src/commonMain/kotlin/com/just/theme/App.kt:309-396`
- 可编辑数据结构：`composeApp/src/commonMain/kotlin/com/just/theme/App.kt:398-447`
- 编辑器状态构造：`composeApp/src/commonMain/kotlin/com/just/theme/App.kt:449-498`
- 由编辑状态构建 `ColorScheme`：`composeApp/src/commonMain/kotlin/com/just/theme/App.kt:500-549`
- 字段行与拾色器按钮：`composeApp/src/commonMain/kotlin/com/just/theme/App.kt:551-572`
- 拾色器弹窗：`composeApp/src/commonMain/kotlin/com/just/theme/App.kt:574-623`
- 十六进制解析：`composeApp/src/commonMain/kotlin/com/just/theme/App.kt:625-647`
- 十六进制格式化：`composeApp/src/commonMain/kotlin/com/just/theme/App.kt:649-656`

## 自定义主题

- 通过编辑页即时修改主题，或在代码中自定义并传入：

```kotlin
val custom = lightColorScheme(
    primary = Color(0xFF6200EE),
    // ... 其他字段
)
MaterialTheme(colorScheme = custom) {
    // 应用内容
}
```

## 目录结构

- `/composeApp/src/commonMain/kotlin`：共享的 Compose 代码
- `/composeApp/src/androidMain`、`/composeApp/src/jvmMain`、`/composeApp/src/jsMain`、`/composeApp/src/wasmJsMain`、`/composeApp/src/iosMain`：各平台特定入口与资源
- `/iosApp`：iOS 应用入口（Xcode 工程）

## 参考

- Kotlin Multiplatform：https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html
- Compose Multiplatform：https://github.com/JetBrains/compose-multiplatform
- Kotlin/Wasm：https://kotl.in/wasm/
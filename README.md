# Theme — Compose Multiplatform Material3 ColorScheme 学习与演示

Theme 是一个基于 Compose Multiplatform（Android/iOS/Web/Desktop）的 Material3 `ColorScheme` 学习与演示项目。
它提供完整的 UI 示例来展示每个颜色角色的使用位置，并内置颜色编辑器与拾色器，帮助你快速理解与定制主题配色。

## 特性

- **ColorScheme 示例页**：逐组展示 `primary/secondary/tertiary`、`surface/background`、`outline`、`error`、`inverse`、`scrim` 等角色的典型用法，并配有详细说明。
- **容器层级展示**：演示 `surfaceContainer*` 与 `surfaceBright/surfaceDim` 的层级建议色。
- **动态色固定角色**：演示 `*Fixed/*FixedDim` 及对应 `on*` 的展示区块。
- **暗色模式支持**：一键切换 Light/Dark 主题，查看配色在不同模式下的表现。
- **动态取色 (Dynamic Color)**：集成 `material-kolor`，支持从种子颜色（Seed Color）自动生成符合 Material 3 标准的完整 Light/Dark 配色方案。
- **实时颜色编辑**：可直接编辑 `ColorScheme` 的所有字段（支持十六进制 `#RRGGBB`/`#AARRGGBB`），即时应用到主题。
- **内置拾色器**：提供 RGBA 滑条与预览联动，支持一键写回字段并同步主题。
- **代码导出**：支持将当前编辑好的配色方案一键导出为 Kotlin 代码（`val myScheme = ColorScheme(...)`），方便直接复制到项目中使用。
- **跨平台支持**：一套代码运行在 Android, iOS, Desktop, Web (Wasm/JS) 上。

## 运行

- **Android (Windows)**:
  - `./gradlew.bat :composeApp:assembleDebug`
- **Desktop (JVM)**:
  - `./gradlew.bat :composeApp:run`
- **Web (Wasm)**:
  - `./gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun`
- **Web (JS)**:
  - `./gradlew.bat :composeApp:jsBrowserDevelopmentRun`
- **iOS**:
  - 在 IDE 的运行配置中使用 iOS 目标，或打开 [/iosApp](./iosApp) 目录到 Xcode 运行。

## 使用说明

1. **浏览示例与主题切换**：
   - 打开应用进入“ColorScheme 示例”页。
   - 点击顶部导航栏的 **☀️/🌙 图标** 切换 Light/Dark 模式。
   - 点击顶部导航栏的 **🎨 (调色板) 图标** 使用“动态取色”功能：选择一个种子颜色，应用会自动生成并应用协调的 Light/Dark 主题。
   - 上下滑动查看不同颜色角色在实际组件中的表现。

2. **编辑与导出主题**：
   - 点击右上角“编辑颜色”进入编辑页。
   - 修改任意字段的十六进制颜色，主题会即时更新（支持分别编辑 Light 和 Dark 方案）。
   - 点击字段右侧“拾色器”图标，使用可视化滑条调整颜色。
   - 点击顶部 **Share (分享/导出) 图标**，可生成当前配色方案的 Kotlin 代码，复制即可使用。
   - 点击顶部“重置”按钮可恢复默认的 Material3 主题。

## 项目结构与关键文件

项目采用模块化设计，将展示逻辑、UI 组件与工具类分离，便于维护与扩展。

- **`composeApp/src/commonMain/kotlin/com/just/theme/`**
  - **`App.kt`**: 应用入口与主导航逻辑。负责管理全局状态（如当前主题颜色、编辑模式状态）以及页面切换。
  - **`sections/ControlSections.kt`**: 包含所有独立的展示区块（Section）。每个函数（如 `PrimaryFamilySection`, `BackgroundSection`）对应示例页中的一个卡片，封装了该颜色角色的 UI 展示逻辑。
  - **`ui/SharedComponents.kt`**: 通用 UI 组件库。包含 `Section`（区块标题与布局容器）、`LabeledSwitch`（带标签的开关）等复用组件。
  - **`util/ColorUtils.kt`**: 颜色处理工具类。提供十六进制字符串与 `Color` 对象之间的相互转换逻辑。

## 自定义主题开发

你可以通过编辑页即时修改主题，也可以在代码中直接定义主题配置：

```kotlin
val custom = lightColorScheme(
    primary = Color(0xFF6200EE),
    // ... 其他字段
)

MaterialTheme(colorScheme = custom) {
    // 应用内容
}
```

## 参考

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform)
- [Material Design 3 - Color](https://m3.material.io/styles/color/overview)

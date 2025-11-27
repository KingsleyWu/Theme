# 贡献指南

感谢你对 Theme 项目感兴趣！我们欢迎任何形式的贡献，包括提交 Bug、建议新功能或直接提交代码。

## 提交 Issue

如果你发现了 Bug 或有新功能建议，请先搜索现有的 [Issues](https://github.com/KingsleyWu/Theme/issues)，看看是否已经有人提出过类似的问题。
如果是一个新问题，请创建一个新的 Issue，并包含以下信息：

- **问题描述**：清晰描述你遇到的问题或建议的功能。
- **复现步骤**：如果是 Bug，请提供复现步骤。
- **环境信息**：例如运行的平台（Android/iOS/Desktop/Web）、操作系统版本等。

## 提交 Pull Request (PR)

如果你想直接贡献代码，请遵循以下步骤：

1. **Fork 本仓库**：点击右上角的 "Fork" 按钮。
2. **创建分支**：在你的 Fork 中创建一个新的分支，例如 `feature/new-section` 或 `fix/typo`。
3. **编写代码**：进行你的修改。请确保代码风格与现有代码保持一致。
4. **运行测试**：确保你的修改没有破坏现有功能。对于本项目，请运行 `./gradlew build` 确保编译通过。
5. **提交 PR**：将你的分支 Push 到你的 Fork，然后在原仓库提交 Pull Request。

## 代码规范

- **Kotlin**：遵循官方 Kotlin 编码规范。
- **Compose**：遵循 Jetpack Compose 最佳实践。
- **模块化**：新增的展示区块请放在 `sections/` 目录下，并在 `App.kt` 中调用。
- **文档**：为新增的公共函数或类添加 KDoc 注释。

## 联系方式

如有任何疑问，请通过 Issue 或邮件联系维护者。

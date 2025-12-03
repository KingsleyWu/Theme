package com.just.theme.ui.screens.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.just.theme.sections.ControlSections
import com.just.theme.ui.responsive.AppContentType
import com.just.theme.ui.responsive.LocalAppLayoutConfig

@Composable
fun ThemePreviewScreen() {
    val layoutConfig = LocalAppLayoutConfig.current
    var textValue by remember { mutableStateOf("") }
    var iconTypeGlobal by remember { mutableStateOf(0) }
    var iconSizeGlobal by remember { mutableStateOf(24f) }
    var iconPaddingGlobal by remember { mutableStateOf(8f) }
    var showOverlay by remember { mutableStateOf(false) }

    // 根据内容类型（单面板/双面板）调整列数
    val minColumnSize = when (layoutConfig.contentType) {
        AppContentType.DUAL_PANE -> 400.dp // 在大屏上每列更宽，避免过于拥挤
        AppContentType.SINGLE_PANE -> 350.dp
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = minColumnSize),
        contentPadding = PaddingValues(16.dp),
        verticalItemSpacing = 24.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            ControlSections.PrimaryFamilySection()
        }
        item(span = StaggeredGridItemSpan.FullLine) {
            ControlSections.ButtonsSection(
                iconTypeGlobal = iconTypeGlobal,
                iconSizeGlobal = iconSizeGlobal,
                iconPaddingGlobal = iconPaddingGlobal,
                onIconTypeChange = { iconTypeGlobal = it },
                onIconSizeChange = { iconSizeGlobal = it },
                onIconPaddingChange = { iconPaddingGlobal = it },
            )
        }
        item(span = StaggeredGridItemSpan.FullLine) {
            ControlSections.InteractionSection(
                iconTypeGlobal = iconTypeGlobal,
                iconSizeGlobal = iconSizeGlobal,
                iconPaddingGlobal = iconPaddingGlobal,
                onIconSizeChange = { iconSizeGlobal = it },
                onIconPaddingChange = { iconPaddingGlobal = it },
            )
        }
        item {
            ControlSections.SecondaryFamilySection()
        }
        item {
            ControlSections.TertiaryFamilySection()
        }
        item {
            ControlSections.SurfaceContentSection()
        }
        item {
            ControlSections.BackgroundSection(textValue = textValue, onTextChange = { textValue = it })
        }
        item {
            ControlSections.OutlineSection()
        }
        item {
            ControlSections.TextFieldStatesSection(textValue = textValue, onTextChange = { textValue = it })
        }
        item {
            ControlSections.ErrorFeedbackSection()
        }
        item {
            ControlSections.ProgressSection()
        }
        item {
            ControlSections.SelectionControlsSection()
        }
        item {
            ControlSections.SnackbarInverseSection()
        }
        item {
            ControlSections.ScrimSection(showOverlay = showOverlay, onShowOverlayChange = { showOverlay = it })
        }
        item {
            ControlSections.SurfaceContainersSection()
        }
        item {
            ControlSections.FixedColorsSection()
        }
    }
}

package cdu278.repetition.item.ui.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cdu278.ui.composable.halfMargin
import kotlin.math.max

val RepetitionProgressHeight: Dp = 10.dp

@Composable
fun RepetitionProgress(
    progressRatio: Double,
    modifier: Modifier = Modifier,
) {
    val color = MaterialTheme.colorScheme.primary
    val sectionsPaddingPx: Float
    val cornerRadiusPx: Float
    val strokeWidthPx: Float
    val mainSectionMinWidthPx: Float
    with(LocalDensity.current) {
        sectionsPaddingPx = halfMargin.toPx()
        cornerRadiusPx = 5.dp.toPx()
        strokeWidthPx = 2.dp.toPx()
        mainSectionMinWidthPx = 10.dp.toPx()
    }
    Spacer(
        modifier = modifier
            .drawBehind {
                val progressSectionWidth =
                    max(
                        ((size.width * progressRatio) - sectionsPaddingPx / 2).toFloat(),
                        mainSectionMinWidthPx
                    )
                drawRoundRect(
                    color,
                    size = Size(
                        width = progressSectionWidth,
                        height = size.height
                    ),
                    cornerRadius = CornerRadius(x = cornerRadiusPx, y = cornerRadiusPx),
                )
                drawRoundRect(
                    color,
                    style = Stroke(width = strokeWidthPx),
                    topLeft = Offset(
                        x = progressSectionWidth + sectionsPaddingPx,
                        y = 0f,
                    ),
                    size = Size(
                        width = (size.width - progressSectionWidth - sectionsPaddingPx),
                        height = size.height
                    ),
                    cornerRadius = CornerRadius(x = cornerRadiusPx, y = cornerRadiusPx),
                )
            }
    )
}
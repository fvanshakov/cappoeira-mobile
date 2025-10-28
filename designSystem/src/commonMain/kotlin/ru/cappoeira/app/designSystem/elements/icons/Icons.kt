package ru.cappoeira.app.designSystem.elements.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import ru.cappoeira.app.designSystem.elements.colors.DesignColors
import ru.cappoeira.app.topbar.BackArrowIcon
import ru.cappoeira.app.topbar.CloseCrossIcon

@Composable
fun CloseIcon(
    isMainTheme: Boolean = true,
    onClick: () -> Unit,
) {
    CloseCrossIcon(
        modifier = Modifier
            .padding(start = 8.dp)
            .size(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onClick.invoke()
                    }
                )
            }
            .indication(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        color = if (isMainTheme) Color.White else Color.Black,
        strokeWidth = 8f
    )
}

@Composable
fun BackIcon(
    isMainTheme: Boolean = true,
    onClick: () -> Unit
) {

    BackArrowIcon(
        modifier = Modifier
            .size(24.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onClick.invoke()
                    }
                )
            }
            .indication(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        color = if (isMainTheme) Color.White else Color.Black,
        strokeWidth = 8f
    )
}

@Composable
fun AlertCircleIcon(
    color: Color = Color.Red,
    modifier: Modifier = Modifier.size(24.dp)
) {
    val icon = ImageVector.Builder(
        name = "AlertCircle",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        // КРУГ (обводка)
        path(
            fill = SolidColor(color),
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f
        ) {
            moveTo(12f, 2f)
            arcToRelative(
                a = 10f,
                b = 10f,
                theta = 0f,
                isMoreThanHalf = true,
                isPositiveArc = true,
                dx1 = 0f,
                dy1 = 20f
            )
            arcToRelative(
                a = 10f,
                b = 10f,
                theta = 0f,
                isMoreThanHalf = true,
                isPositiveArc = true,
                dx1 = 0f,
                dy1 = -20f
            )
            close()
        }

        // Вертикальная палочка "!"
        path(
            fill = SolidColor(Color.Black)
        ) {
            moveTo(11.5f, 7f)
            lineTo(12.5f, 7f)
            lineTo(12.5f, 13f)
            lineTo(11.5f, 13f)
            close()
        }

        // Точка "!"
        path(
            fill = SolidColor(Color.Black)
        ) {
            moveTo(11.5f, 16f)
            lineTo(12.5f, 16f)
            lineTo(12.5f, 17f)
            lineTo(11.5f, 17f)
            close()
        }
    }.build()

    Image(
        painter = rememberVectorPainter(icon),
        contentDescription = "Alert",
        modifier = modifier
    )
}

@Composable
fun PlayIcon(
    onClick: () -> Unit
) {
    val icon = ImageVector.Builder(
        name = "PlayIcon",
        defaultWidth = 50.dp,
        defaultHeight = 50.dp,
        viewportWidth = 50f,
        viewportHeight = 50f
    ).apply {
        path(
            fill = SolidColor(DesignColors.Orange),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(16f, 10f)
            lineTo(38f, 24f)
            lineTo(16f, 38f)
            close()
        }
    }.build()

    Image(
        painter = rememberVectorPainter(icon),
        contentDescription = "Alert",
        modifier = Modifier.clickable { onClick() }
    )
}
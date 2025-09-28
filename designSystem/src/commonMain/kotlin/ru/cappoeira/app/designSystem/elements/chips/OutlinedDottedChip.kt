package ru.cappoeira.app.designSystem.elements.chips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedDottedChip(
    Content: @Composable (() -> Unit)
) {
    val stroke = Stroke(
        width = 8f,
        pathEffect = PathEffect.
        chainPathEffect(
            outer = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f),
            inner = PathEffect.cornerPathEffect(50f)
        )
    )

    Row(
        horizontalArrangement = Arrangement.Absolute.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .drawBehind {
                drawRoundRect(color = Color.White, style = stroke)
            }
    ) {
        Content()
    }
}
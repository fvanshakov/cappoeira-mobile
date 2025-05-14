package ru.cappoeira.app.designSystem.elements.chips

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedChip(
    onClick: () -> Unit,
    isShimmering: Boolean = false,
    Content: @Composable (() -> Unit)
) {
    val transition = rememberInfiniteTransition()
    val shimmerTranslate by transition.animateFloat(
        initialValue = 0f,
        targetValue = 3000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.White.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f)
        ),
        start = Offset(x = shimmerTranslate - 1000f, y = 0f),
        end = Offset(x = shimmerTranslate, y = 0f)
    )

    val stroke = Stroke(
        width = 10f,
        pathEffect = PathEffect.cornerPathEffect(50f)
    )

    var modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(vertical = 8.dp)
        .drawBehind {
            drawRoundRect(color = Color.Black, style = stroke)
        }

    if (isShimmering) {
        modifier = modifier
            .clip(RoundedCornerShape(36))
            .background(shimmerBrush)
    }

    Row(
        horizontalArrangement = Arrangement.Absolute.Center,
        modifier = modifier
            .clickable { onClick() }
    ) {
        Content()
    }
}
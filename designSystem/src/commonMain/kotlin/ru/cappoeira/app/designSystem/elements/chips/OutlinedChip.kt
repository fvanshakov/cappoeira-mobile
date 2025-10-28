package ru.cappoeira.app.designSystem.elements.chips

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedChip(
    color: Color = Color.Black,
    onClick: () -> Unit = {},
    isShimmering: Boolean = false,
    alpha: Float = 1.0f,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Absolute.SpaceAround,
    modifier: Modifier = Modifier
        .padding(horizontal = 16.dp)
        .padding(vertical = 8.dp),
    Content: @Composable (() -> Unit),
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

    val shimmerBrush = remember { Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.White.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f)
        ),
        start = Offset.Zero,
        end = Offset(1000f, 0f)
    ) }

    Row(
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(15.dp))
            .border(
                width = 3.dp,
                color = color.copy(alpha = alpha),
                shape = RoundedCornerShape(15.dp)
            )
            .clickable { onClick() }
            .then(
                if (isShimmering) Modifier.background(shimmerBrush) else Modifier
            )
    ) {
        Content()
    }
}
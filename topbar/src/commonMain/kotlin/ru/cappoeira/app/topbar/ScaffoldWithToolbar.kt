package ru.cappoeira.app.topbar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ScaffoldWithToolbar(
    screenName: String,
    onBackButton: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {

    Scaffold(
        topBar = { TopBar(onBackButton, screenName) }
    ) {
        content()
    }
}

@Composable
fun TopBar(
    onBackButton: (() -> Unit)? = null,
    screenName: String
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = screenName,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Start,
            )
        },
        navigationIcon = {
            onBackButton?.let {
                IconButton(
                    onClick = { onBackButton() }
                ) {
                    BackArrowIcon(
                        modifier = Modifier.size(20.dp),
                        color = Color.Black
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
    )
}

@Composable
fun BackArrowIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    strokeWidth: Float = 4f
) {
    Canvas(modifier = modifier.size(24.dp)) {
        val width = size.width
        val height = size.height

        val start = Offset(x = width * 0.7f, y = height * 0.2f)
        val middle = Offset(x = width * 0.3f, y = height * 0.5f)
        val end = Offset(x = width * 0.7f, y = height * 0.8f)

        drawLine(
            color = color,
            start = start,
            end = middle,
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        drawLine(
            color = color,
            start = end,
            end = middle,
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun CloseCrossIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    strokeWidth: Float = 4f
) {
    Canvas(modifier = modifier.size(24.dp)) {
        val width = size.width
        val height = size.height

        drawLine(
            color = color,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = width, y = height),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        drawLine(
            color = color,
            start = Offset(x = 0f, y = height),
            end = Offset(x = width, y = 0f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}
package ru.cappoeira.app.designSystem.elements.icons

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun CloseIcon(
    isMainTheme: Boolean = true,
    onClick: () -> Unit,
) {
    Icon(
        imageVector = Icons.Filled.Close,
        contentDescription = "Close",
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
        tint = if (isMainTheme) Color.White else Color.Black,
    )
}

@Composable
fun BackIcon(
    isMainTheme: Boolean = true,
    onClick: () -> Unit
) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "Back",
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
        tint = if (isMainTheme) Color.White else Color.Black,
    )
}
package ru.cappoeira.app.designSystem.elements.icons

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import ru.cappoeira.app.topbar.BackArrowIcon
import ru.cappoeira.app.topbar.CloseCrossIcon

@Composable
fun CloseIcon(
    isMainTheme: Boolean = true,
    onClick: () -> Unit,
) {
    CloseCrossIcon(
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
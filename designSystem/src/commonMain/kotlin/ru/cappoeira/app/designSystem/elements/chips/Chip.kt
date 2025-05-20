package ru.cappoeira.app.designSystem.elements.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.cappoeira.app.designSystem.elements.colors.DesignColors
import ru.cappoeira.app.designSystem.elements.icons.CloseIcon

@Composable
fun Chip(
    isSelected: Boolean,
    text: String,
    isClosable: Boolean = false,
    onClick: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null
) {

    Row(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = if (isSelected) DesignColors.Orange else Color.LightGray
            )
            .indication(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onClick?.invoke()
                    }
                )
            }
            .padding(horizontal = 16.dp)
            .height(36.dp)
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text.uppercase(),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = if (isSelected) Color.White else Color.Black,
            textAlign = TextAlign.Center,
        )
        if (isClosable) {
            CloseIcon { onClose?.invoke() }
        }
    }
}
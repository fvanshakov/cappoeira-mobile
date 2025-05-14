package ru.cappoeira.app.designSystem.elements.texts

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GeneralText(
    text: String,
    color: Color? = null
) {
    Text(
        text = text,
        modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = color ?: Color.Black,
    )
}
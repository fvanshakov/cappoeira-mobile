package ru.cappoeira.app.designSystem.elements.gaps

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SimpleGap(
    height: Dp = 4.dp
) {
    Spacer(modifier = Modifier.height(height))
}
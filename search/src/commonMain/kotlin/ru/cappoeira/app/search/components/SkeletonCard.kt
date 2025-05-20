package ru.cappoeira.app.search.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.cappoeira.app.designSystem.elements.chips.OutlinedChip

@Composable
fun SkeletonCard() {

    OutlinedChip(
        isShimmering = true,
        onClick = { }
    ) {
        Spacer(
            modifier = Modifier
                .height(36.dp)
                .fillMaxWidth()
        )
    }
}
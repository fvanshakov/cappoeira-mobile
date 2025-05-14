package ru.cappoeira.app.designSystem.elements.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.cappoeira.app.designSystem.elements.colors.DesignColors

@Composable
fun SwipeableTopbar(
    listState: LazyListState,
    content: @Composable () -> Unit
) {
    var isTopBarVisible by remember { mutableStateOf(true) }

    LaunchedEffect(listState) {
        var lastScrollOffset = 0
        snapshotFlow { listState.firstVisibleItemIndex }.collect { offset ->
            if (offset > lastScrollOffset) {
                isTopBarVisible = false
            } else if (offset < lastScrollOffset) {
                isTopBarVisible = true
            }
            lastScrollOffset = offset
        }
    }

    AnimatedVisibility(
        visible = isTopBarVisible,
        enter = slideInVertically(
            initialOffsetY = { -600.dp.value.toInt() }
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { -600.dp.value.toInt() }
        ) + fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Color.Black,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 32.dp,
                        bottomEnd = 32.dp
                    )
                )
        ) {
            content()
        }
    }
}
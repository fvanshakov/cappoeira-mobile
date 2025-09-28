package ru.cappoeira.app.designSystem.elements.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SwipeableBottomBar(
    listState: LazyListState,
    content: @Composable () -> Unit,
) {
    var isBottomBarVisible by remember { mutableStateOf(true) }

    LaunchedEffect(listState) {
        var lastScrollOffset = 0
        snapshotFlow { listState.firstVisibleItemIndex }.collect { offset ->
            if (offset > lastScrollOffset) {
                isBottomBarVisible = false
            } else if (offset < lastScrollOffset) {
                isBottomBarVisible = true
            }
            lastScrollOffset = offset
        }
    }

    AnimatedVisibility(
        visible = isBottomBarVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp,
                            topStart = 32.dp,
                            topEnd = 32.dp
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    }
}
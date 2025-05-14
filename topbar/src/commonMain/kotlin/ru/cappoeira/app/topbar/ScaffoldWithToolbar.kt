package ru.cappoeira.app.topbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Кнопка назад")
                }
            }
        },
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
    )
}
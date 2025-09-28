package ru.cappoeira.app.designSystem.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.franshizastr.linkopener.LinkOpener
import ru.cappoeira.app.designSystem.elements.colors.DesignColors

@Composable
fun BugButton(modifier: Modifier) {
    FloatingActionButton(
        onClick = {
            LinkOpener().openUrl("https://t.me/+lOrPtET6nhA0MTIx")
        },
        modifier = modifier
            .padding(16.dp),
        backgroundColor = DesignColors.Orange
    ) {
        Icon(
            imageVector = DesignIcons.BugReport,
            contentDescription = "Cообщить о баге"
        )
    }
}

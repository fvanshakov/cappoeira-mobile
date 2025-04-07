package ru.cappoeira.app.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.cappoeira.app.search.models.SongInfoViewObject

@Composable
fun SongCard(
    songInfoVO: SongInfoViewObject,
    onClickOnCard: (id: String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onClickOnCard(songInfoVO.id) }
        ) {
            Text(
                text = songInfoVO.songName,
                style = MaterialTheme.typography.caption
            )
        }
    }
}
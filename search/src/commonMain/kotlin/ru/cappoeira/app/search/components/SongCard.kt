package ru.cappoeira.app.search.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import ru.cappoeira.app.designSystem.elements.chips.OutlinedChip
import ru.cappoeira.app.designSystem.elements.texts.GeneralText
import ru.cappoeira.app.search.models.SongInfoViewObject

@Composable
fun SongCard(
    songInfoVO: SongInfoViewObject,
    onClickOnCard: (id: String, songName: String) -> Unit
) {
    OutlinedChip(
        onClick = { onClickOnCard(songInfoVO.id, songInfoVO.songName) }
    ) {
        Row {
            GeneralText(songInfoVO.songName)
        }
    }
}
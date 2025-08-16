package ru.cappoeira.app.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.cappoeira.app.designSystem.elements.chips.OutlinedChip
import ru.cappoeira.app.designSystem.elements.delimiter.SimpleDelimiter
import ru.cappoeira.app.designSystem.elements.gaps.SimpleGap
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                GeneralText(songInfoVO.songName)
            }
            if (songInfoVO.songLines.isNotEmpty()) {
                SimpleDelimiter()
                SimpleGap()
                songInfoVO.songLines.forEach {
                    GeneralText(it.text, verticalPadding = 4.dp)
                }
            }
        }
    }
}
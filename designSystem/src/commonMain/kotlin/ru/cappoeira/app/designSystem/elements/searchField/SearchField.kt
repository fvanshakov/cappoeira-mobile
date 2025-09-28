package ru.cappoeira.app.designSystem.elements.searchField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SearchField(
    searchText: String,
    onTextChanged: (newValue: String) -> Unit
) {
    var search by remember { mutableStateOf(searchText) }

    OutlinedTextField(
        value = search,
        onValueChange = {
            search = it
            onTextChanged(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        label = null,
        singleLine = true,
        placeholder = {
            Text(
                text = "ПОИСК",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
            )
        },
        textStyle = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
        ),
        colors = textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            placeholderColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White,
            disabledPlaceholderColor = Color.White,
        ),
        shape = RoundedCornerShape(16.dp),
    )
}
package com.lesa.topfilms.ui.screens.films

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lesa.topfilms.models.Film

@Composable
fun FilmList(
    films: List<Film>,
    onSelect: (Film) -> Unit,
    onLongClick: (Film) -> Unit,
    padding: PaddingValues,
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = contentPadding
    ) {
        items(films) {film ->
            FilmCard(
                film = film,
                onClick = onSelect,
                onLongClick = onLongClick
            )
        }
    }
}
package com.lesa.topfilms.ui.screens.films.fav_films

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.lesa.topfilms.R
import com.lesa.topfilms.models.Film
import com.lesa.topfilms.ui.screens.LoadingView
import com.lesa.topfilms.ui.screens.films.EmptyView
import com.lesa.topfilms.ui.screens.films.FilmList

@Composable
fun FavFilmsScreen(
    onSelect: (Film) -> Unit,
    padding: PaddingValues,
    contentPadding: PaddingValues,
    viewModel: FavFilmsViewModel
) {

    val context = LocalContext.current
    when (val favFilmsUiState = viewModel.favFilmsUiState.collectAsState().value) {
        is FavFilmsUiState.Loading -> LoadingView()
        is FavFilmsUiState.Success -> {
            val toastMessage = stringResource(id = R.string.toast_delete_from_fav)
            FilmList(
                films = favFilmsUiState.films,
                onSelect = onSelect,
                onLongClick = {
                    viewModel.onLongClick(it)
                    Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
                },
                padding = padding,
                contentPadding = contentPadding
            )
        }
        is FavFilmsUiState.Empty -> EmptyView(
            message = R.string.message_empty_fav
        )
    }
}
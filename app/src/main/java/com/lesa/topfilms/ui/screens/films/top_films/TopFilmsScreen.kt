package com.lesa.topfilms.ui.screens.films.top_films

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getString
import com.lesa.topfilms.R
import com.lesa.topfilms.models.Film
import com.lesa.topfilms.ui.screens.ErrorView
import com.lesa.topfilms.ui.screens.LoadingView
import com.lesa.topfilms.ui.screens.films.FilmList
import kotlinx.coroutines.flow.StateFlow

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun TopFilmsScreen(
    onSelect: (Film) -> Unit,
    padding: PaddingValues,
    contentPadding: PaddingValues,
    searchText: StateFlow<String?>,
    viewModel: TopFilmsViewModel
) {
    val context = LocalContext.current
    when (val topFilmsUiState = viewModel.topFilmsUiState.collectAsState().value) {
        is TopFilmsUiState.Error -> ErrorView(
            onRepeat = { viewModel.onRepeatButtonClicked() }
        )
        is TopFilmsUiState.Loading -> LoadingView()
        is TopFilmsUiState.Success -> {
            FilmList(
                films = topFilmsUiState.films,
                onSelect = onSelect,
                onLongClick = {
                    viewModel.onLongClick(it)
                    val toastMessage = if (!it.isFav)
                        getString(context, R.string.toast_add_to_fav)
                    else
                        getString(context, R.string.toast_delete_from_fav)
                    Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
                },
                padding = padding,
                contentPadding = contentPadding
            )
        }
    }
}
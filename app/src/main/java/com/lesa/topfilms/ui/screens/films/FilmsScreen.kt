
package com.lesa.topfilms.ui.screens.films

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lesa.topfilms.R
import com.lesa.topfilms.models.Film
import com.lesa.topfilms.ui.components.TFButton
import com.lesa.topfilms.ui.components.TFButtonStyle
import com.lesa.topfilms.ui.screens.TFSearchBar
import com.lesa.topfilms.ui.screens.films.fav_films.FavFilmsScreen
import com.lesa.topfilms.ui.screens.films.fav_films.FavFilmsViewModel
import com.lesa.topfilms.ui.screens.films.top_films.TopFilmsScreen
import com.lesa.topfilms.ui.screens.films.top_films.TopFilmsViewModel
import com.lesa.topfilms.ui.theme.TFBlue

@OptIn(ExperimentalMaterial3Api::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun FilmsScreen(
    onSelect: (Film) -> Unit
) {
    val filmsViewModel: FilmsViewModel = viewModel(factory = FilmsViewModel.Factory)
    val topFilmsViewModel: TopFilmsViewModel = viewModel(factory = TopFilmsViewModel.Factory())
    val favFilmsViewModel: FavFilmsViewModel = viewModel(factory = FavFilmsViewModel.Factory)
    val isSearching by filmsViewModel.isSearching.collectAsState()

    Scaffold(
        topBar = {
            if (!isSearching) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = filmsViewModel.state.collectAsState().value.title),
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    actions = {
                        Row(
                            Modifier.padding(end = 16.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    filmsViewModel.onToggleSearch()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = stringResource(id = R.string.search),
                                    tint = TFBlue
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )
            } else {
                TFSearchBar(
                    onBack = {
                        filmsViewModel.onToggleSearch()
                        if (filmsViewModel.state.value is FilmsState.TopFilms) {
                            topFilmsViewModel.searchInDb(null)
                        } else {
                            favFilmsViewModel.searchInDb(null)
                        }
                    },
                    onQueryChange = {
                        if (filmsViewModel.state.value is FilmsState.TopFilms) {
                            topFilmsViewModel.searchInDb(it.ifEmpty { null })
                        } else {
                            favFilmsViewModel.searchInDb(it.ifEmpty { null })
                        }
                    }
                )
            }

        },
        containerColor = Color.White
    ) { paddingValues ->
        Box {
            Column(
                Modifier.fillMaxSize()
            ) {
                when (filmsViewModel.state.collectAsState().value) {
                    is FilmsState.TopFilms -> {
                        TopFilmsScreen(
                            onSelect = onSelect,
                            padding = paddingValues,
                            contentPadding = PaddingValues(bottom = (45 + 16 + 16).dp),
                            searchText = filmsViewModel.searchText,
                            viewModel = topFilmsViewModel
                        )
                    }
                    is FilmsState.FavFilms -> {
                        FavFilmsScreen(
                            onSelect = onSelect,
                            padding = paddingValues,
                            contentPadding = PaddingValues(bottom = (45 + 16 + 16).dp),
                            viewModel = favFilmsViewModel
                        )
                    }
                }
                Spacer(modifier = Modifier.height(45.dp))
            }
            Footer(
                viewModel = filmsViewModel,
                rowModifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Transparent)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun Footer(
    viewModel: FilmsViewModel,
    rowModifier: Modifier
) {
    val state = viewModel.state.collectAsState().value
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = rowModifier
    ) {
        TFButton(
            text = R.string.top_title,
            modifier = Modifier
                .weight(1f),
            style = if (state is FilmsState.TopFilms) {
                TFButtonStyle.PRIMARY
            } else {
                TFButtonStyle.SECONDARY
            },
            onClick = {
                viewModel.onTopFilmsButtonClicked()
            }
        )
        TFButton(
            text = R.string.fav_title,
            modifier = Modifier
                .weight(1f),
            style = if (state is FilmsState.FavFilms) {
                TFButtonStyle.PRIMARY
            } else {
                TFButtonStyle.SECONDARY
            },
            onClick = {
                viewModel.onFavFilmsButtonClicked()
            }
        )
    }
}
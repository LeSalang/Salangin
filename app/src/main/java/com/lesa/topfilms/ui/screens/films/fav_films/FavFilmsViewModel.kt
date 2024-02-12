package com.lesa.topfilms.ui.screens.films.fav_films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lesa.topfilms.TopFilmsApplication
import com.lesa.topfilms.data.FilmRepository
import com.lesa.topfilms.models.Film
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class FavFilmsUiState {
    class Loading: FavFilmsUiState()
    class Empty: FavFilmsUiState()
    class Success(val films: List<Film>): FavFilmsUiState()
}

class FavFilmsViewModel(
    private val filmRepository: FilmRepository
): ViewModel() {
    var favFilmsUiState: MutableStateFlow<FavFilmsUiState> = MutableStateFlow(FavFilmsUiState.Empty())
        private set

    init {
        getFavFilms()
    }

    private fun getFavFilms() {
        viewModelScope.launch {
            favFilmsUiState.value = FavFilmsUiState.Empty()
            filmRepository.favFilms.collect {
                favFilmsUiState.value = if (it.isEmpty()) {
                    FavFilmsUiState.Empty()
                } else {
                    FavFilmsUiState.Success(
                        films = it
                    )
                }
                FavFilmsUiState.Success(films = it)
            }
        }
    }

    fun onLongClick(film: Film) {
        viewModelScope.launch {
            val fav = film.copy(isFav = false)
            filmRepository.updateFilm(fav)
        }
    }

    fun searchInDb(name: String?) {
        viewModelScope.launch {
            if (name != null) {
                filmRepository.searchFromFav(name = name).collect {
                    favFilmsUiState.value = FavFilmsUiState.Success(films = it)
                }
            } else {
                filmRepository.favFilms.collect {
                    favFilmsUiState.value = FavFilmsUiState.Success(films = it)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TopFilmsApplication)
                val filmRepository = application.container.filmRepository
                FavFilmsViewModel(filmRepository = filmRepository)
            }
        }
    }
}
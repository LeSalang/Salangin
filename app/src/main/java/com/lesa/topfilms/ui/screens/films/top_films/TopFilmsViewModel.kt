package com.lesa.topfilms.ui.screens.films.top_films

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

sealed class TopFilmsUiState {
    class Error: TopFilmsUiState()
    class Loading: TopFilmsUiState()
    class Success(val films: List<Film>): TopFilmsUiState()
}

class TopFilmsViewModel(
    private val filmRepository: FilmRepository,
): ViewModel() {

    var topFilmsUiState: MutableStateFlow<TopFilmsUiState> = MutableStateFlow(TopFilmsUiState.Loading())
        private set

    fun onLongClick(film: Film) {
        viewModelScope.launch {
            val fav = film.copy(isFav = film.isFav.not())
            filmRepository.updateFilm(fav)
        }
    }

    init {
        getTopFilms()
    }

    fun onRepeatButtonClicked() {
        getTopFilms()
    }

    private fun getTopFilms() {
        viewModelScope.launch {
            topFilmsUiState.value = TopFilmsUiState.Loading()
            try {
                filmRepository.getTopFilms()
                filmRepository.topFilms.collect {
                    topFilmsUiState.value = TopFilmsUiState.Success(films = it)
                }
            } catch (e: Exception) {
                topFilmsUiState.value = TopFilmsUiState.Error()
            }
        }
    }

    fun searchInDb(name: String?) {
        viewModelScope.launch {
            if (name != null) {
                filmRepository.search(name = name).collect {
                    topFilmsUiState.value = TopFilmsUiState.Success(films = it)
                }
            } else {
                filmRepository.topFilms.collect {
                    topFilmsUiState.value = TopFilmsUiState.Success(films = it)
                }
            }
        }
    }

    companion object {
        fun Factory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TopFilmsApplication)
                val filmRepository = application.container.filmRepository
                TopFilmsViewModel(
                    filmRepository = filmRepository,
                )
            }
        }
    }
}
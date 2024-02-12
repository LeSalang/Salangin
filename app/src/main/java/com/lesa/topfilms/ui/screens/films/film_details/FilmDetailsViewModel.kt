package com.lesa.topfilms.ui.screens.films.film_details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lesa.topfilms.TopFilmsApplication
import com.lesa.topfilms.data.FilmRepository
import com.lesa.topfilms.models.Film
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class FilmDetailsUiState {
    class Error: FilmDetailsUiState()
    class Loading: FilmDetailsUiState()
    class Success(val film: Film): FilmDetailsUiState()
    class Empty: FilmDetailsUiState()
}
class FilmDetailsViewModel(
    private val filmRepository: FilmRepository,
): ViewModel() {

    var filmDetailsUiState: MutableStateFlow<FilmDetailsUiState> = MutableStateFlow(
        FilmDetailsUiState.Empty())
        private set

    private var id: Int? = null

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    fun getFilmDetails(id: Int?) {
        this.id = id
        viewModelScope.launch {
            if (id == null) {
                filmDetailsUiState.value = FilmDetailsUiState.Empty()
            } else {
                filmDetailsUiState.value = FilmDetailsUiState.Loading()
                try {
                    filmRepository.loadFilm(id)
                } catch (e: Exception) {
                    sendMessage("Ошибка сети!")
                }
                filmRepository.getFilmById(id).collect {
                    if (it == null) {
                        filmDetailsUiState.value = FilmDetailsUiState.Error()
                    } else {
                        filmDetailsUiState.value = FilmDetailsUiState.Success(film = it)
                    }
                }
            }
        }
    }

    fun onRepeatButtonClicked() {
        getFilmDetails(id)
    }
}

class FilmDetailsViewModelFactory(
    private val context: Context
    ): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val application = context.applicationContext as TopFilmsApplication
        val filmRepository = application.container.filmRepository
        return FilmDetailsViewModel(
            filmRepository = filmRepository,
        ) as T
    }
}

package com.lesa.topfilms.ui.screens.films

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lesa.topfilms.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class FilmsState {
    class TopFilms(@StringRes override val title: Int = R.string.top_title): FilmsState()
    class FavFilms(@StringRes override val title: Int = R.string.fav_title): FilmsState()
    abstract val title: Int
}

class FilmsViewModel: ViewModel() {

    private var mutableState = MutableStateFlow<FilmsState>(FilmsState.TopFilms())

    val state = mutableState.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToggleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }
    fun onTopFilmsButtonClicked() {
        mutableState.value = FilmsState.TopFilms()
    }

    fun onFavFilmsButtonClicked() {
        mutableState.value = FilmsState.FavFilms()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                FilmsViewModel()
            }
        }
    }
}
package com.lesa.topfilms.ui.navigation

import androidx.annotation.StringRes

enum class NavScreens(
    val route: String
) {
    TopFilms(route = "TopFilms"),
    Favourites(route = "Favourites"),
    Film(route = "Film")
}
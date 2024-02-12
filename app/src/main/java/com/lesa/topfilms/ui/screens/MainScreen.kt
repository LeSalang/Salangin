package com.lesa.topfilms.ui.screens

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.PaneScaffoldDirective
import androidx.compose.material3.adaptive.calculateStandardPaneScaffoldDirective
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.lesa.topfilms.ui.screens.films.FilmsScreen
import com.lesa.topfilms.ui.screens.films.film_details.FilmDetailsScreen
import com.lesa.topfilms.ui.screens.films.film_details.FilmDetailsViewModel
import com.lesa.topfilms.ui.screens.films.film_details.FilmDetailsViewModelFactory

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MainScreen() {
    val stateHandle = SavedStateHandle()
    val viewModel: FilmDetailsViewModel = viewModel(
        factory = FilmDetailsViewModelFactory(
            context = LocalContext.current
        )
    )
    val navController = rememberNavController()
    val windowInfo = currentWindowAdaptiveInfo()
    val navigator =
        rememberListDetailPaneScaffoldNavigator<Nothing>(
            scaffoldDirective =
            calculateStandardPaneScaffoldDirective(windowInfo).let {
                PaneScaffoldDirective(
                    contentPadding = PaddingValues(0.dp),
                    maxHorizontalPartitions = it.maxHorizontalPartitions,
                    horizontalPartitionSpacerSize = it.horizontalPartitionSpacerSize,
                    maxVerticalPartitions = it.maxVerticalPartitions,
                    verticalPartitionSpacerSize = it.verticalPartitionSpacerSize,
                    excludedBounds = it.excludedBounds,
                )
            }
        )
    var selectedFilmId: Int? by rememberSaveable {
        mutableStateOf(null)
    }
    ListDetailPaneScaffold(
        scaffoldState = navigator.scaffoldState,
        listPane = {
            FilmsScreen(
                onSelect = {
                    viewModel.getFilmDetails(it.id)
                    selectedFilmId = it.id
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                }
            )
        },
        detailPane = {
            FilmDetailsScreen(
                viewModel = viewModel,
                onBack = {
                    navigator.navigateBack()
                    viewModel.getFilmDetails(null)
                }
            )
        },
        windowInsets = WindowInsets(0.dp)
    )
}
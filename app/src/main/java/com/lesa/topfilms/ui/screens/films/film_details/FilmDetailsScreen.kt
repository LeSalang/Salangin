package com.lesa.topfilms.ui.screens.films.film_details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.lesa.topfilms.R
import com.lesa.topfilms.models.Film
import com.lesa.topfilms.ui.screens.ErrorView
import com.lesa.topfilms.ui.screens.LoadingView
import com.lesa.topfilms.ui.screens.films.EmptyView
import com.lesa.topfilms.ui.theme.TFBlue
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.glide.GlideImageState
import com.valentinilk.shimmer.shimmer

@Composable
fun FilmDetailsScreen(
    viewModel: FilmDetailsViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel
            .toastMessage
            .collect { message ->
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_LONG,
                ).show()
            }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            when (val filmDetailsUiState = viewModel.filmDetailsUiState.collectAsState().value) {
                is FilmDetailsUiState.Error -> ErrorView(
                    onRepeat = { viewModel.onRepeatButtonClicked() }
                )
                is FilmDetailsUiState.Loading -> LoadingView()
                is FilmDetailsUiState.Success -> {
                    val film = filmDetailsUiState.film
                    FilmDetailsContent(
                        film = film
                    )
                }
                is FilmDetailsUiState.Empty -> EmptyView(message = R.string.message_empty_details)
            }
        }
        if (viewModel.filmDetailsUiState.collectAsState().value !is FilmDetailsUiState.Empty) {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "back",
                    tint = TFBlue
                )
            }
        }
    }
}

@Composable
fun FilmDetailsContent(
    film: Film
) {
    val isImageLoading = remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top

    ) {
        val shimmerModifier = if (isImageLoading.value) {
            Modifier.shimmer()
        } else {
            Modifier
        }
        Box(
            modifier = shimmerModifier
                .aspectRatio(0.66f, false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(TFBlue)
            )
            GlideImage(
                imageModel = { film.posterUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                requestOptions = {
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                },
                onImageStateChanged = {
                    if (it is GlideImageState.Success) isImageLoading.value = false
                }
            )
        }
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = film.name,
                style = MaterialTheme.typography.titleMedium
            )
            film.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.genres),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = film.genres.joinToString(", "),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.countries),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = film.countries.joinToString(", "),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
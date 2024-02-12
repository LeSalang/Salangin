package com.lesa.topfilms.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lesa.topfilms.R
import com.lesa.topfilms.ui.theme.TFBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TFSearchBar(
    onBack: () -> Unit,
    onQueryChange: (String) -> Unit,
) {
    var queryString by remember {
        mutableStateOf("")
    }
    var isActive by remember {
        mutableStateOf(false)
    }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        query = queryString,
        onQueryChange = { newQueryString ->
            queryString = newQueryString
            onQueryChange(newQueryString)
        },
        onSearch = {
            isActive = false
        },
        active = isActive,
        onActiveChange = { activeChange ->
            isActive = activeChange
        },
        placeholder = {
            Text(text = stringResource(id = R.string.message_search_holder))
        },
        leadingIcon = {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "back",
                    tint = TFBlue
                )
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = Color.White,
            inputFieldColors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    ) { }
}
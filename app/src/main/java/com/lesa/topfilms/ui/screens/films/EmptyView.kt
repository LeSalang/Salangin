package com.lesa.topfilms.ui.screens.films

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lesa.topfilms.R
import com.lesa.topfilms.ui.theme.TFBlue

@Composable
fun EmptyView(
    @StringRes message: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.favourite),
            contentDescription = null,
            modifier = Modifier,
            colorFilter = ColorFilter.tint(TFBlue)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = message),
            style = MaterialTheme.typography.bodyMedium,
            color = TFBlue,
            textAlign = TextAlign.Center
        )
    }
}
package com.lesa.topfilms.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lesa.topfilms.ui.theme.TFBlue
import com.lesa.topfilms.ui.theme.TFLightBlue

enum class TFButtonStyle(val containerColor: Color, val contentColor: Color) {
    PRIMARY(containerColor = TFBlue, contentColor = Color.White),
    SECONDARY(containerColor = TFLightBlue, contentColor = TFBlue)
}

@Composable
fun TFButton(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: TFButtonStyle = TFButtonStyle.PRIMARY
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = style.containerColor,
            contentColor = style.contentColor
        ),
        shape = RoundedCornerShape(100.dp),
        modifier = modifier
            .height(45.dp)

    ) {
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.labelMedium,
            color = style.contentColor
        )
    }
}
package com.sample.presentation.util.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.presentation.R
import com.sample.presentation.theme.secondaryColor

@Composable
fun IndeterminateCircularIndicator(loading: Boolean) {
    if (!loading) return
    CustomerCircularProgressBar()
}

@Composable
fun CustomerCircularProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .width(52.dp)
                .height(52.dp)
                .padding(top = 24.dp),
            alignment = Alignment.Center,
            contentDescription = "cooking_icon",
            painter = painterResource(R.drawable.ic_cooking_bowl)
        )
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = secondaryColor,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Preview
@Composable
fun PreviewLoader() {
    IndeterminateCircularIndicator(true)
}
package com.sample.presentation.feature.recipedetails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.sample.presentation.coreui.IndeterminateCircularIndicator
import com.sample.presentation.feature.recipedetails.intent.RecipeDetailIntent
import com.sample.presentation.feature.recipedetails.uistate.RecipeDetailUiState
import com.sample.presentation.feature.recipedetails.viewmodel.RecipeDetailViewModel

@Composable
fun RecipeDetailScreen(
    innerPadding: PaddingValues,
    navController: NavHostController,
    recipeId: String,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    viewModel.handleEvents(RecipeDetailIntent.LoadPage)
    val data by viewModel.recipeDetail.collectAsState()
    when (data) {
        is RecipeDetailUiState.DataLoaded -> {
            IndeterminateCircularIndicator(false)
            RecipeDetailScreenContent(
                data as RecipeDetailUiState.DataLoaded,
                navController,
                innerPadding
            )
        }

        RecipeDetailUiState.LOADING -> IndeterminateCircularIndicator(true)
        is RecipeDetailUiState.ErrorState -> {
            IndeterminateCircularIndicator(false)
            Text(text = (data as RecipeDetailUiState.ErrorState).errorMessage)
            Button(
                modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                onClick = {
                    viewModel.handleEvents(RecipeDetailIntent.LoadPage)
                }
            ) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun RecipeDetailScreenContent(
    data: RecipeDetailUiState.DataLoaded,
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(innerPadding)
    ) {
        AsyncImage(
            modifier = Modifier.aspectRatio(1f),
            model = data.imageUrl,
            contentDescription = "Translated description of what the image contains"
        )
        Text(
            text = data.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = W700,
            color = Color.Black
        )
        Row {
            Text(
                text = "Prep Time: ",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = W700
            )
            Text(
                text = data.cookTimeMinutes.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = W700
            )
        }
        Text(
            text = data.description,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = W700
        )
    }
}

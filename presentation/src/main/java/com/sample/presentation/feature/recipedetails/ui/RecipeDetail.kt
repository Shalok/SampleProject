package com.sample.presentation.feature.recipedetails.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sample.presentation.R
import com.sample.presentation.feature.recipedetails.intent.RecipeDetailIntent
import com.sample.presentation.feature.recipedetails.uistate.RecipeDetailUiState
import com.sample.presentation.feature.recipedetails.viewmodel.RecipeDetailViewModel
import com.sample.presentation.theme.primaryColor
import com.sample.presentation.util.compose.IndeterminateCircularIndicator
import com.sample.presentation.util.compose.RecipeCard
import com.sample.presentation.util.model.RecipeCardData

@Composable
fun RecipeDetailScreen(
    innerPadding: PaddingValues,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    viewModel.handleEvents(RecipeDetailIntent.LoadPage)
    val data by viewModel.recipeDetail.collectAsState()
    when (data) {
        is RecipeDetailUiState.DataLoaded -> {
            RecipeDetailScreenContent(
                data as RecipeDetailUiState.DataLoaded,
                innerPadding
            )
        }

        RecipeDetailUiState.LOADING -> IndeterminateCircularIndicator(true)
        is RecipeDetailUiState.ErrorState -> {
            Text(
                text = (data as RecipeDetailUiState.ErrorState).errorMessage
                    ?: stringResource(R.string.generic_error_msg)
            )
            Button(
                onClick = {
                    viewModel.handleEvents(RecipeDetailIntent.LoadPage)
                }
            ) {
                Text(text = stringResource(R.string.text_retry))
            }
        }
    }
}

@Composable
fun RecipeDetailScreenContent(
    data: RecipeDetailUiState.DataLoaded,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        RecipeCard(
            RecipeCardData(
                id = data.id,
                name = data.name,
                imageUrl = data.imageUrl,
                cookingTime = data.cookTimeMinutes,
                difficulty = data.difficulty,
                cuisine = data.cuisine
            ),
            cardRoundedCornerShape = RoundedCornerShape(0.dp)
        )
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.spacer_height_medium)))
        InfoCard(
            stringResource(R.string.ingredient),
            R.drawable.ic_ingredient,
            listOf(data.ingredients)
        )
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.spacer_height_medium)))
        InfoCard(
            stringResource(R.string.instructions),
            R.drawable.ic_bullet,
            data.instructions
        )
    }
}

@Composable
private fun InfoCard(
    title: String,
    @DrawableRes icon: Int,
    data: List<String>
) {
    Text(
        title,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        color = primaryColor
    )
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.standard_elevation_medium),
            pressedElevation = 5.dp
        ),
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.standard_padding_medium))
                .fillMaxWidth()
        ) {
            data.map {
                Row {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = "cooking_icon"
                    )
                    Text(text = it)
                }
            }
        }
    }
}

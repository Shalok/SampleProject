package com.sample.presentation.feature.allrecipes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sample.presentation.R
import com.sample.presentation.feature.allrecipes.intent.AllRecipesIntent
import com.sample.presentation.feature.allrecipes.uistate.AllRecipesUiState
import com.sample.presentation.feature.allrecipes.uistate.AllRecipesUiState.DataLoadedUiState
import com.sample.presentation.feature.allrecipes.viewmodel.AllRecipeViewModel
import com.sample.presentation.navigation.NavigationItem
import com.sample.presentation.util.compose.IndeterminateCircularIndicator
import com.sample.presentation.util.compose.RecipeCard
import com.sample.presentation.util.model.RecipeCardData

@Composable
fun AllRecipeScreen(
    innerPadding: PaddingValues,
    navController: NavHostController,
    homeViewModel: AllRecipeViewModel = hiltViewModel()
) {
    homeViewModel.handleEvent(AllRecipesIntent.LoadPage)
    val data by homeViewModel.allRecipes.collectAsState()
    val sendIntent by rememberUpdatedState(homeViewModel::handleEvent)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(innerPadding)
    ) {
        when (data) {
            is DataLoadedUiState -> {
                ListRecipe(data as DataLoadedUiState, navController)
            }

            AllRecipesUiState.LOADING -> IndeterminateCircularIndicator(true)
            is AllRecipesUiState.ErrorUiState -> {
                Text(
                    text = (data as AllRecipesUiState.ErrorUiState).errorMessage ?: stringResource(
                        R.string.generic_error_msg
                    )
                )
                Button(
                    onClick = {
                        sendIntent(AllRecipesIntent.RetryPageLoad)
                    }
                ) {
                    Text(text = stringResource(R.string.text_retry))
                }
            }
        }
    }
}

@Composable
fun ListRecipe(data: DataLoadedUiState, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.padding(dimensionResource(R.dimen.standard_padding_medium))
    ) {
        items(data.recipesList) {
            RecipeCard(
                RecipeCardData(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    cookingTime = it.cookingTime,
                    difficulty = it.difficulty,
                    cuisine = it.cuisine,
                    modifier = Modifier.padding(dimensionResource(R.dimen.standard_padding_medium)),
                    callback = { recipeId ->
                        navController.navigate(NavigationItem.RecipeDetail(recipeId))
                    }
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height_medium)))
        }
    }
}

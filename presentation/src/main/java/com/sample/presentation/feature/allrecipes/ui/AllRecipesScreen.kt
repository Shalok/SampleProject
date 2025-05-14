package com.sample.presentation.feature.allrecipes.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.sample.presentation.coreui.IndeterminateCircularIndicator
import com.sample.presentation.feature.allrecipes.intent.AllRecipesIntent
import com.sample.presentation.feature.allrecipes.uistate.AllRecipesUiState
import com.sample.presentation.feature.allrecipes.uistate.AllRecipesUiState.DataLoadedUiState
import com.sample.presentation.feature.allrecipes.viewmodel.AllRecipeViewModel
import com.sample.presentation.feature.recipedetails.intent.RecipeDetailIntent
import com.sample.presentation.feature.recipedetails.uistate.RecipeDetailUiState
import com.sample.presentation.navigation.NavigationItem

@Composable
fun AllRecipeScreen(
    innerPadding: PaddingValues,
    navController: NavHostController,
    homeViewModel: AllRecipeViewModel = hiltViewModel()
) {
    homeViewModel.handleEvent(AllRecipesIntent.LoadPage)
    val data by homeViewModel.allRecipes.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(innerPadding)
    ) {
        when (data) {
            is DataLoadedUiState -> {
                IndeterminateCircularIndicator(false)
                ListRecipe(data as DataLoadedUiState, navController)
            }

            AllRecipesUiState.LOADING -> IndeterminateCircularIndicator(true)
            is AllRecipesUiState.ErrorUiState -> {
                IndeterminateCircularIndicator(false)
                Text(text = (data as AllRecipesUiState.ErrorUiState).errorMessage)
                Button(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    onClick = {
                        homeViewModel.handleEvent(AllRecipesIntent.LoadPage)
                    }
                ) {
                    Text(text = "Retry")
                }
                IndeterminateCircularIndicator(false)
            }
        }
    }
}

@Composable
fun ListRecipe(data: DataLoadedUiState, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ) {
        items(data.recipesList) {
            Card(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(NavigationItem.RecipeDetail(it.id))
                    },
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 5.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        AsyncImage(
                            modifier = Modifier.aspectRatio(1f),
                            model = it.imageUrl,
                            contentDescription = "Translated description of what the image contains"
                        )
                        Text(
                            text = it.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

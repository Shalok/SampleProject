package com.sample.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sample.presentation.feature.allrecipes.ui.AllRecipeScreen
import com.sample.presentation.feature.recipedetails.ui.RecipeDetailScreen

@Composable
fun AppNavigationHost(
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    NavHost(navController, startDestination = NavigationItem.AllRecipes) {
        composable<NavigationItem.AllRecipes> {
            AllRecipeScreen(
                innerPadding,
                navController
            )
        }
        composable<NavigationItem.RecipeDetail> {
            RecipeDetailScreen(
                innerPadding
            )
        }
    }
}
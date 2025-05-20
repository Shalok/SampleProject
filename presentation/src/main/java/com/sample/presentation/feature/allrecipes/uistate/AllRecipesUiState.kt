package com.sample.presentation.feature.allrecipes.uistate

sealed interface AllRecipesUiState {

    data object LOADING : AllRecipesUiState

    data class ErrorUiState(
        val errorMessage: String?
    ) : AllRecipesUiState

    data class DataLoadedUiState(
        val totalRecipes: Int,
        val recipesList: List<RecipeUiState>
    ) : AllRecipesUiState

    data class RecipeUiState(
        val name: String,
        val imageUrl: String,
        val prepTime: String,
        val id: String,
        val cookingTime: String,
        val difficulty: String,
        val cuisine: String,
        val caloriesPerServing: String
    )
}
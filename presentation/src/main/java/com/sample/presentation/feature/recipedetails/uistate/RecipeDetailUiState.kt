package com.sample.presentation.feature.recipedetails.uistate

sealed class RecipeDetailUiState {
    data object LOADING : RecipeDetailUiState()
    data class ErrorState(val errorMessage: String) : RecipeDetailUiState()
    data class DataLoaded(
        val name: String,
        val imageUrl: String,
        val description: String,
        val id: String,
        val ingredients: String,
        val instructions: List<String>,
        val prepTimeMinutes: Int,
        val cookTimeMinutes: String,
        val servings: Int,
        val difficulty: String,
        val cuisine: String,
        val caloriesPerServing: Int,
        val tags: List<String>,
        val userId: Int,
        val rating: Double
    ) : RecipeDetailUiState()
}
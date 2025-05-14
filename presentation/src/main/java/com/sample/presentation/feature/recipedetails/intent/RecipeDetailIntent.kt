package com.sample.presentation.feature.recipedetails.intent

sealed class RecipeDetailIntent {
    data object LoadPage : RecipeDetailIntent()
}
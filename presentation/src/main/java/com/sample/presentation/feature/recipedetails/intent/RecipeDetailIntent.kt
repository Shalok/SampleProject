package com.sample.presentation.feature.recipedetails.intent

sealed interface RecipeDetailIntent {
    data object LoadPage : RecipeDetailIntent
}
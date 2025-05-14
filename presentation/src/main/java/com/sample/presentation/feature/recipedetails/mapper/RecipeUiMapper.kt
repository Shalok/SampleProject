package com.sample.presentation.feature.recipedetails.mapper

import com.sample.domain.allrecipes.model.Recipe
import com.sample.presentation.feature.recipedetails.uistate.RecipeDetailUiState
import jakarta.inject.Inject

class RecipeUiMapper @Inject constructor() {

    fun invoke(recipe: Recipe): RecipeDetailUiState {
        return RecipeDetailUiState.DataLoaded(
            name = recipe.name ?: "",
            imageUrl = recipe.image ?: "",
            description = recipe.cuisine ?: "",
            id = recipe.id?.toString() ?: "",
            ingredients = recipe.ingredients,
            instructions = recipe.instructions,
            prepTimeMinutes = recipe.prepTimeMinutes ?: 0,
            cookTimeMinutes = recipe.cookTimeMinutes ?: 0,
            servings = recipe.servings ?: 0,
            difficulty = recipe.difficulty ?: "",
            cuisine = recipe.cuisine ?: "",
            caloriesPerServing = recipe.caloriesPerServing ?: 0,
            tags = recipe.tags,
            userId = recipe.userId ?: 0,
            rating = recipe.rating ?: 0.0
        )
    }
}
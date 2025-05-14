package com.sample.domain.recipedetail

import com.sample.core.networking.Result
import com.sample.domain.allrecipes.model.Recipe

interface RecipeDetailRepository {
    suspend fun getRecipeDetails(recipeId: String): Result<Recipe>
}
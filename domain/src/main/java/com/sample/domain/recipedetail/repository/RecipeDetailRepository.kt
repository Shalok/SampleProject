package com.sample.domain.recipedetail.repository

import com.sample.core.networking.Result
import com.sample.domain.recipedetail.entities.Recipe

interface RecipeDetailRepository {
    suspend fun getRecipeDetails(recipeId: String): Result<Recipe>
}
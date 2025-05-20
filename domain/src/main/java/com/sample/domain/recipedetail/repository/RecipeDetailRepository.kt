package com.sample.domain.recipedetail.repository

import com.sample.core.networking.utility.Result
import com.sample.domain.recipedetail.entities.Recipe

interface RecipeDetailRepository {
    suspend fun getRecipeDetails(recipeId: String): Result<Recipe>
}
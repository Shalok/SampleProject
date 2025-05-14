package com.sample.data.recipedetail

import com.sample.core.networking.Result
import com.sample.data.recipedetail.entity.RecipesDto

interface RecipeDetailRepository {
    suspend fun getRecipeDetails(recipeId: String): Result<RecipesDto>
}
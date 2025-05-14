package com.sample.domain.recipedetail

import com.sample.core.networking.Result
import com.sample.domain.allrecipes.model.Recipe
import jakarta.inject.Inject

class RecipeDetailUseCase @Inject constructor(
    private val recipeDetailRepository: RecipeDetailRepository
) {
    suspend operator fun invoke(recipeId: String): Result<Recipe> {
        return recipeDetailRepository.getRecipeDetails(recipeId)

    }
}
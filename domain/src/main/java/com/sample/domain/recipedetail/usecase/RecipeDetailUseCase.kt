package com.sample.domain.recipedetail.usecase

import com.sample.core.networking.utility.Result
import com.sample.domain.recipedetail.entities.Recipe
import com.sample.domain.recipedetail.repository.RecipeDetailRepository
import jakarta.inject.Inject

class RecipeDetailUseCase @Inject constructor(
    private val recipeDetailRepository: RecipeDetailRepository
) {
    suspend operator fun invoke(recipeId: String): Result<Recipe> {
        return recipeDetailRepository.getRecipeDetails(recipeId)
    }
}
package com.sample.domain.recipedetail

import com.sample.core.networking.Result
import com.sample.domain.allrecipes.model.Recipe
import com.sample.data.recipedetail.RecipeDetailRepository
import com.sample.domain.mapper.RecipeDtoMapper
import jakarta.inject.Inject

class RecipeDetailUseCase @Inject constructor(
    private val recipeDetailRepository: RecipeDetailRepository,
    private val recipeDtoMapper: RecipeDtoMapper
) {
    suspend fun invoke(recipeId: String): Result<Recipe> {
        return recipeDetailRepository.getRecipeDetails(recipeId).let {
            return@let when (it) {
                is Result.Success -> {
                    Result.Success(recipeDtoMapper.invoke(it.data))
                }

                is Result.Error -> {
                    it
                }
            }
        }

    }
}
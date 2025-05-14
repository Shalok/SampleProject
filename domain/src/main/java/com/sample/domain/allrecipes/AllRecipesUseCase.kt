package com.sample.domain.allrecipes

import com.sample.core.networking.Result
import com.sample.data.allrecipes.AllRecipesRepository
import com.sample.domain.allrecipes.model.AllRecipes
import com.sample.domain.mapper.AllRecipesDtoMapper
import jakarta.inject.Inject

class AllRecipesUseCase @Inject constructor(
    private val allRecipesRepository: AllRecipesRepository,
    private val allRecipesDtoMapper: AllRecipesDtoMapper
) {
    suspend operator fun invoke(): Result<AllRecipes> =
        allRecipesRepository.getAllRecipes().let {
            return@let when (it) {
                is Result.Success -> {
                    Result.Success(allRecipesDtoMapper.invoke(it.data))
                }
                is Result.Error -> {
                    it
                }
            }
        }
}
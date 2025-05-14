package com.sample.domain.allrecipes

import com.sample.core.networking.Result
import com.sample.domain.allrecipes.model.AllRecipes
import jakarta.inject.Inject

class AllRecipesUseCase @Inject constructor(
    private val allRecipesRepository: AllRecipesRepository
) {
    suspend operator fun invoke(): Result<AllRecipes> =
        allRecipesRepository.getAllRecipes()
}
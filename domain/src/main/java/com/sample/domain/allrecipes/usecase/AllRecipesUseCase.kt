package com.sample.domain.allrecipes.usecase

import com.sample.core.networking.utility.Result
import com.sample.domain.allrecipes.entities.AllRecipes
import com.sample.domain.allrecipes.repository.AllRecipesRepository
import jakarta.inject.Inject

class AllRecipesUseCase @Inject constructor(
    private val allRecipesRepository: AllRecipesRepository
) {
    suspend operator fun invoke(): Result<AllRecipes> =
        allRecipesRepository.getAllRecipes()
}
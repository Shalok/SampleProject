package com.sample.data.allrecipes.impl

import com.sample.core.networking.network.contract.ApiExecutor
import com.sample.core.networking.utility.Result
import com.sample.data.mapper.AllRecipesDtoMapper
import com.sample.data.services.RecipesApiServices
import com.sample.domain.allrecipes.entities.AllRecipes
import com.sample.domain.allrecipes.repository.AllRecipesRepository
import jakarta.inject.Inject

internal class AllRecipesRepositoryImpl @Inject constructor(
    private val recipesApiServices: RecipesApiServices,
    private val allRecipesDtoMapper: AllRecipesDtoMapper,
    private val apiExecutor: ApiExecutor
) : AllRecipesRepository {

    override suspend fun getAllRecipes(): Result<AllRecipes> {
        val result = apiExecutor { recipesApiServices.getAllRecipes() }
        return when (result) {
            is Result.Success -> Result.Success(allRecipesDtoMapper(result.data))
            is Result.Error -> Result.Error(result.throwable)
        }
    }
}
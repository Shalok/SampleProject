package com.sample.data.recipedetail.impl

import com.sample.core.networking.utility.Result
import com.sample.core.networking.network.contract.ApiExecutor
import com.sample.data.services.RecipesApiServices
import com.sample.domain.recipedetail.entities.Recipe
import com.sample.domain.recipedetail.repository.RecipeDetailRepository
import jakarta.inject.Inject

internal class RecipeDetailRepositoryImpl @Inject constructor(
    private val recipesApiServices: RecipesApiServices,
    private val recipeDtoMapper: com.sample.data.mapper.RecipeDtoMapper,
    private val apiExecutor: ApiExecutor
) : RecipeDetailRepository {
    override suspend fun getRecipeDetails(recipeId: String): Result<Recipe> {

        val result = apiExecutor{recipesApiServices.getRecipeDetail(recipeId)}
        return when(result){
            is Result.Success -> Result.Success(recipeDtoMapper(result.data))
            is Result.Error -> Result.Error(result.throwable)
        }
    }
}
package com.sample.data.recipedetail.impl

import com.sample.core.networking.Result
import com.sample.domain.recipedetail.repository.RecipeDetailRepository
import com.sample.data.services.RecipesApiServices
import com.sample.domain.recipedetail.entities.Recipe
import jakarta.inject.Inject

internal class RecipeDetailRepositoryImpl @Inject constructor(
    private val recipesApiServices: RecipesApiServices,
    private val recipeDtoMapper: com.sample.data.mapper.RecipeDtoMapper
) : RecipeDetailRepository {
    override suspend fun getRecipeDetails(recipeId: String): Result<Recipe> {
        try {
            val response = recipesApiServices.getRecipeDetail(recipeId)
            if (response.isSuccessful) {
                return response.body()?.let {
                    Result.Success(recipeDtoMapper.invoke(it))
                } ?: kotlin.run {
                    Result.Error(Exception("Response body is null"))
                }
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is Exception -> {
                    return Result.Error(throwable)
                }
            }
        }
        return Result.Error(Exception("Response body is null"))
    }
}
package com.sample.data.recipedetail.impl

import com.sample.core.networking.Result
import com.sample.data.recipedetail.RecipeDetailRepository
import com.sample.data.recipedetail.entity.RecipesDto
import com.sample.data.services.RecipesApiServices
import jakarta.inject.Inject

internal class RecipeDetailRepositoryImpl @Inject constructor(
    private val recipesApiServices: RecipesApiServices
) : RecipeDetailRepository {
    override suspend fun getRecipeDetails(recipeId: String): Result<RecipesDto> {
        try {
            val response = recipesApiServices.getRecipeDetail(recipeId)
            if (response.isSuccessful) {
                return response.body()?.let {
                    Result.Success(it)
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
package com.sample.data.allrecipes.impl

import com.sample.core.networking.Result
import com.sample.domain.allrecipes.AllRecipesRepository
import com.sample.data.mapper.AllRecipesDtoMapper
import com.sample.data.services.RecipesApiServices
import com.sample.domain.allrecipes.model.AllRecipes
import jakarta.inject.Inject

internal class AllRecipesRepositoryImpl @Inject constructor(
    private val recipesApiServices: RecipesApiServices,
    private val allRecipesDtoMapper: AllRecipesDtoMapper
) : AllRecipesRepository {

    override suspend fun getAllRecipes(): Result<AllRecipes> {
        try {
            val response = recipesApiServices.getAllRecipes()
            if (response.isSuccessful) {
                return response.body()?.let {
                    Result.Success(allRecipesDtoMapper.invoke(it))
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
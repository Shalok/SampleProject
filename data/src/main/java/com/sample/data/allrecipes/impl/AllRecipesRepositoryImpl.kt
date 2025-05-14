package com.sample.data.allrecipes.impl

import com.sample.core.networking.Result
import com.sample.data.allrecipes.AllRecipesRepository
import com.sample.data.allrecipes.entity.AllRecipesDto
import com.sample.data.services.RecipesApiServices
import jakarta.inject.Inject

internal class AllRecipesRepositoryImpl @Inject constructor(
    private val recipesApiServices: RecipesApiServices
) : AllRecipesRepository {

    override suspend fun getAllRecipes(): Result<AllRecipesDto> {
        try {
            val response = recipesApiServices.getAllRecipes()
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
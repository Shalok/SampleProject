package com.sample.data.allrecipes

import com.sample.core.networking.Result
import com.sample.data.allrecipes.entity.AllRecipesDto

interface AllRecipesRepository {
    suspend fun getAllRecipes() : Result<AllRecipesDto>
}
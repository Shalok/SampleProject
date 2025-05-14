package com.sample.domain.allrecipes

import com.sample.core.networking.Result
import com.sample.domain.allrecipes.model.AllRecipes

interface AllRecipesRepository {
    suspend fun getAllRecipes() : Result<AllRecipes>
}
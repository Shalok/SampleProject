package com.sample.domain.allrecipes.repository

import com.sample.core.networking.Result
import com.sample.domain.allrecipes.entities.AllRecipes

interface AllRecipesRepository {
    suspend fun getAllRecipes(): Result<AllRecipes>
}
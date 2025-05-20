package com.sample.domain.allrecipes.repository

import com.sample.core.networking.utility.Result
import com.sample.domain.allrecipes.entities.AllRecipes

interface AllRecipesRepository {
    suspend fun getAllRecipes(): Result<AllRecipes>
}
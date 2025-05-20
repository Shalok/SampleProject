package com.sample.data.services

import com.sample.data.allrecipes.dto.AllRecipesDto
import com.sample.data.recipedetail.dto.RecipesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipesApiServices {
    @GET("recipes")
    suspend fun getAllRecipes(): Response<AllRecipesDto>

    @GET("recipes/{recipeId}")
    suspend fun getRecipeDetail(@Path("recipeId") recipeId: String): Response<RecipesDto>
}
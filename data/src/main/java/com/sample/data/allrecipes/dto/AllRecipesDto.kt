package com.sample.data.allrecipes.dto

import com.google.gson.annotations.SerializedName
import com.sample.data.recipedetail.dto.RecipesDto

data class AllRecipesDto(
    @SerializedName("recipes") val recipes: List<RecipesDto>,
    @SerializedName("total") val total: Int? = null
)
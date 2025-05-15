package com.sample.data.allrecipes.dtos

import com.google.gson.annotations.SerializedName
import com.sample.data.recipedetail.entity.RecipesDto

data class AllRecipesDto(
    @SerializedName("recipes") val recipes: List<RecipesDto> = listOf(),
    @SerializedName("total") val total: Int? = null
)
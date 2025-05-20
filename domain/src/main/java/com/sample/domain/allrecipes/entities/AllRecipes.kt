package com.sample.domain.allrecipes.entities

import com.sample.domain.recipedetail.entities.Recipe

data class AllRecipes(
    val recipes: List<Recipe>,
    val total: Int?,
)

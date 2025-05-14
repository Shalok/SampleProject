package com.sample.domain.allrecipes.model

data class AllRecipes(
    val recipes: List<Recipe> = listOf(),
    val total: Int? = null,
)

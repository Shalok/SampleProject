package com.sample.domain.recipedetail.entities

data class Recipe(
    val id: Int? = null,
    val name: String? = null,
    val ingredients: List<String>,
    val instructions: List<String>,
    val prepTimeMinutes: Int? = null,
    val cookTimeMinutes: Int? = null,
    val servings: Int? = null,
    val difficulty: String? = null,
    val cuisine: String? = null,
    val caloriesPerServing: Int? = null,
    val tags: List<String>,
    val userId: Int? = null,
    val image: String? = null,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val mealType: List<String>
)

package com.sample.data.recipedetail.dto

import com.google.gson.annotations.SerializedName

data class RecipesDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("ingredients") val ingredients: List<String>,
    @SerializedName("instructions") val instructions: List<String>,
    @SerializedName("prepTimeMinutes") val prepTimeMinutes: Int? = null,
    @SerializedName("cookTimeMinutes") val cookTimeMinutes: Int? = null,
    @SerializedName("servings") val servings: Int? = null,
    @SerializedName("difficulty") val difficulty: String? = null,
    @SerializedName("cuisine") val cuisine: String? = null,
    @SerializedName("caloriesPerServing") val caloriesPerServing: Int? = null,
    @SerializedName("tags") val tags: List<String> = listOf(),
    @SerializedName("userId") val userId: Int? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("rating") val rating: Double? = null,
    @SerializedName("reviewCount") val reviewCount: Int? = null,
    @SerializedName("mealType") val mealType: List<String> = listOf()
)

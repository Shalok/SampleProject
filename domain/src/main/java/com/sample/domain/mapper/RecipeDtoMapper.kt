package com.sample.domain.mapper

import com.sample.data.recipedetail.entity.RecipesDto
import com.sample.domain.allrecipes.model.Recipe
import jakarta.inject.Inject

class RecipeDtoMapper @Inject constructor() {

    fun invoke(recipeDto: RecipesDto): Recipe {
        return Recipe(
            id = recipeDto.id,
            name = recipeDto.name,
            ingredients = recipeDto.ingredients,
            instructions = recipeDto.instructions,
            prepTimeMinutes = recipeDto.prepTimeMinutes,
            cookTimeMinutes = recipeDto.cookTimeMinutes,
            servings = recipeDto.servings,
            difficulty = recipeDto.difficulty,
            cuisine = recipeDto.cuisine,
            caloriesPerServing = recipeDto.caloriesPerServing,
            tags = recipeDto.tags,
            userId = recipeDto.userId,
            image = recipeDto.image,
            rating = recipeDto.rating,
            reviewCount = recipeDto.reviewCount,
            mealType = recipeDto.mealType
        )
    }
}
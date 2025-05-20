package com.sample.data.mapper

import com.sample.data.allrecipes.dto.AllRecipesDto
import com.sample.domain.allrecipes.entities.AllRecipes
import jakarta.inject.Inject

class AllRecipesDtoMapper @Inject constructor(
    private val recipeDtoMapper: RecipeDtoMapper
) {

    operator fun invoke(
        allRecipes: AllRecipesDto
    ): AllRecipes {
        return AllRecipes(
            recipes = allRecipes.recipes.map {
                return@map recipeDtoMapper(it)
            },
            total = allRecipes.total
        )
    }
}
package com.sample.data.mapper

import com.sample.data.allrecipes.dtos.AllRecipesDto
import com.sample.domain.allrecipes.entities.AllRecipes
import jakarta.inject.Inject

class AllRecipesDtoMapper @Inject constructor(
    private val recipeDtoMapper: RecipeDtoMapper
) {

    fun invoke(
        allRecipes: AllRecipesDto
    ): AllRecipes {
        return AllRecipes(
            recipes = allRecipes.recipes.map {
                return@map recipeDtoMapper.invoke(it)
            },
            total = allRecipes.total
        )
    }
}
package com.sample.presentation.feature.allrecipes.mapper

import com.sample.domain.allrecipes.model.AllRecipes
import com.sample.presentation.feature.allrecipes.uistate.AllRecipesUiState
import jakarta.inject.Inject

class AllRecipesUiMapper @Inject constructor() {

    fun invoke(allRecipes: AllRecipes): AllRecipesUiState{
        return AllRecipesUiState.DataLoadedUiState(
            totalRecipes = allRecipes.total ?: 0,
            recipesList = allRecipes.recipes.map {
                AllRecipesUiState.RecipeUiState(
                    name = it.name ?: "",
                    imageUrl = it.image ?: "",
                    description = it.cuisine ?: "",
                    id = it.id.toString()
                )
            }
        )
    }
}
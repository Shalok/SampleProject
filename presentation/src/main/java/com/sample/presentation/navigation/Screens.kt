package com.sample.presentation.navigation

import kotlinx.serialization.Serializable

enum class Screens {
    ALL_RECIPE,
    RECIPE_DETAIL
}

@Serializable
sealed class NavigationItem(val des: String) {

    @Serializable
    data object AllRecipes : NavigationItem(Screens.ALL_RECIPE.name)

    @Serializable
    data class RecipeDetail(val recipeId: String) : NavigationItem(Screens.RECIPE_DETAIL.name)
}

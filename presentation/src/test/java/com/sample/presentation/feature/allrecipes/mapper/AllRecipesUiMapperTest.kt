package com.sample.presentation.feature.allrecipes.mapper

import com.sample.domain.allrecipes.entities.AllRecipes
import com.sample.domain.recipedetail.entities.Recipe
import com.sample.presentation.feature.allrecipes.uistate.AllRecipesUiState
import org.junit.Test

class AllRecipesUiMapperTest {

    private val testClass = AllRecipesUiMapper()

    @Test
    fun mapperWithCompleteData(){
        val allRecipes = AllRecipes(
            total = 1,
            recipes = listOf(
                Recipe(
                    id = 1,
                    name = "name",
                    image = "image",
                    cuisine = "cuisine",
                    ingredients = listOf("ingredient"),
                    instructions = listOf("instruction"),
                    prepTimeMinutes = 1,
                    cookTimeMinutes = 1,
                    servings = 1,
                    difficulty = "difficulty",
                    caloriesPerServing = 1,
                    tags = listOf("tag"),
                    userId = 1,
                    rating = 1.0,
                    reviewCount = 1,
                    mealType = listOf("mealType")
                )
        )
        )
        val result = testClass.invoke(allRecipes)
        assert(result is AllRecipesUiState.DataLoadedUiState)
        assert((result as AllRecipesUiState.DataLoadedUiState).totalRecipes == 1)
        assert(result.recipesList.size == 1)
        assert(result.recipesList[0].name == "name")
        assert(result.recipesList[0].imageUrl == "image")
        assert(result.recipesList[0].prepTime == "cuisine")
        assert(result.recipesList[0].id == "1")
    }

    @Test
    fun mapperWithNullData(){
        val allRecipes = AllRecipes(
            total = null,
            recipes = listOf()
        )
        val result = testClass.invoke(allRecipes)
        assert(result is AllRecipesUiState.DataLoadedUiState)
        assert((result as AllRecipesUiState.DataLoadedUiState).totalRecipes == 0)
        assert(result.recipesList.isEmpty())
    }
}
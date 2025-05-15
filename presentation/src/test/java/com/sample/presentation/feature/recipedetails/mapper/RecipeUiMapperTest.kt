package com.sample.presentation.feature.recipedetails.mapper

import com.sample.domain.recipedetail.entities.Recipe
import com.sample.presentation.feature.recipedetails.uistate.RecipeDetailUiState
import org.junit.Test

class RecipeUiMapperTest {

    private val testClass = RecipeUiMapper()

    @Test
    fun mapperWithCompleteData(){
        val recipe = Recipe(
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
        val result = testClass.invoke(recipe)
        assert(result is RecipeDetailUiState.DataLoaded)
        assert((result as RecipeDetailUiState.DataLoaded).name == "name")
        assert(result.imageUrl == "image")
        assert(result.description == "cuisine")
        assert(result.id == "1")
        assert(result.ingredients == listOf("ingredient"))
        assert(result.instructions == listOf("instruction"))
        assert(result.prepTimeMinutes == 1)
        assert(result.cookTimeMinutes == 1)
        assert(result.servings == 1)
        assert(result.difficulty == "difficulty")
        assert(result.cuisine == "cuisine")
        assert(result.caloriesPerServing == 1)
        assert(result.tags == listOf("tag"))
        assert(result.userId == 1)
        assert(result.rating == 1.0)
    }

    @Test
    fun mapperWithNullData(){
        val recipe = Recipe(
            id = null,
            name = null,
            image = null,
            cuisine = null,
            ingredients = emptyList(),
            instructions = emptyList(),
            prepTimeMinutes = null,
            cookTimeMinutes = null,
            servings = null,
            difficulty = null,
            caloriesPerServing = null,
            tags = emptyList(),
            userId = null,
            rating = null,
            reviewCount = null,
            mealType = emptyList()
        )
        val result = testClass.invoke(recipe)
        assert(result is RecipeDetailUiState.DataLoaded)
        assert((result as RecipeDetailUiState.DataLoaded).name == "")
        assert(result.imageUrl == "")
        assert(result.description == "")
        assert(result.id == "")
        assert(result.ingredients.isEmpty())
        assert(result.instructions.isEmpty())
        assert(result.prepTimeMinutes == 0)
        assert(result.cookTimeMinutes == 0)
        assert(result.servings == 0)
        assert(result.difficulty == "")
        assert(result.cuisine == "")
        assert(result.caloriesPerServing == 0)
        assert(result.tags.isEmpty())
        assert(result.userId == 0)
        assert(result.rating == 0.0)
    }

}
package com.sample.presentation.feature.recipedetails.mapper

import com.sample.domain.recipedetail.entities.Recipe
import com.sample.presentation.feature.recipedetails.uistate.RecipeDetailUiState
import org.junit.Assert
import org.junit.Test

class RecipeUiMapperTest {

    private val testClass = RecipeUiMapper()

    @Test
    fun mapperWithCompleteData() {
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
        Assert.assertTrue(result is RecipeDetailUiState.DataLoaded)
        Assert.assertEquals((result as RecipeDetailUiState.DataLoaded).name, "name")
        Assert.assertEquals(result.imageUrl, "image")
        Assert.assertEquals(result.description, "cuisine")
        Assert.assertEquals(result.id, "1")
        Assert.assertEquals(result.ingredients, "ingredient")
        Assert.assertEquals(result.instructions, listOf("instruction"))
        Assert.assertEquals(result.prepTimeMinutes, 1)
        Assert.assertEquals(result.cookTimeMinutes, "1")
        Assert.assertEquals(result.servings, 1)
        Assert.assertEquals(result.difficulty, "difficulty")
        Assert.assertEquals(result.cuisine, "cuisine")
        Assert.assertEquals(result.caloriesPerServing, 1)
        Assert.assertEquals(result.tags, listOf("tag"))
        Assert.assertEquals(result.userId, 1)
    }

    @Test
    fun mapperWithNullData() {
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
        Assert.assertTrue(result is RecipeDetailUiState.DataLoaded)
        Assert.assertEquals((result as RecipeDetailUiState.DataLoaded).name, "")
        Assert.assertEquals(result.imageUrl, "")
        Assert.assertEquals(result.description, "")
        Assert.assertEquals(result.id, "")
        Assert.assertTrue(result.ingredients.isEmpty())
        Assert.assertTrue(result.instructions.isEmpty())
        Assert.assertEquals(result.prepTimeMinutes, 0)
        Assert.assertEquals(result.cookTimeMinutes, "")
        Assert.assertEquals(result.servings, 0)
        Assert.assertEquals(result.difficulty, "")
        Assert.assertEquals(result.cuisine, "")
        Assert.assertEquals(result.caloriesPerServing, 0)
        Assert.assertTrue(result.tags.isEmpty())
        Assert.assertEquals(result.userId, 0)
    }

}
package com.sample.presentation.feature.allrecipes.mapper

import com.sample.domain.allrecipes.entities.AllRecipes
import com.sample.domain.recipedetail.entities.Recipe
import com.sample.presentation.feature.allrecipes.uistate.AllRecipesUiState
import org.junit.Assert
import org.junit.Test

private const val NAME = "Kadhai Paneer"
private const val IMAGE_URL = "imageURL"
private const val CUISINE = "Mexican"
private const val ID = 1
private const val PREP_TIME = 30
private const val COOKING_TIME = 10
private const val DIFFICULTY = "Easy"
private const val CALORIES_PER_SERVING = 100
private const val ID_UI_STATE = "1"
private const val PREP_TIME_UI_STATE = "30"
private const val COOKING_TIME_UI_STATE = "10"
private const val CALORIES_PER_SERVING_UI_STATE = "100"

class AllRecipesUiMapperTest {

    private val testClass = AllRecipesUiMapper()

    @Test
    fun `GIVEN happy flow WHEN invoke with correct data THEN return success`() {
        val result = testClass(getSampleRecipeData())
        Assert.assertEquals(getUiStateSuccessData(), result)
    }

    @Test
    fun `GEVIN empty flow WHEN invoke with empty data THEN return empty UI State`() {
        val allRecipes = AllRecipes(
            total = null,
            recipes = listOf()
        )
        val result = testClass(allRecipes)
        Assert.assertEquals(
            AllRecipesUiState.DataLoadedUiState(
                totalRecipes = 0,
                recipesList = listOf()
            ), result
        )
    }

    private fun getSampleRecipeData() = AllRecipes(
        total = 1,
        recipes = listOf(
            Recipe(
                id = ID,
                name = NAME,
                image = IMAGE_URL,
                cuisine = CUISINE,
                prepTimeMinutes = PREP_TIME,
                cookTimeMinutes = COOKING_TIME,
                difficulty = DIFFICULTY,
                caloriesPerServing = CALORIES_PER_SERVING,
                ingredients = listOf(),
                instructions = listOf(),
                tags = listOf(),
                userId = 1,
                rating = 1.0,
                reviewCount = 1,
                mealType = listOf()
            )
        )
    )

    private fun getUiStateSuccessData() = AllRecipesUiState.DataLoadedUiState(
        totalRecipes = 1,
        recipesList = listOf(
            AllRecipesUiState.RecipeUiState(
                id = ID_UI_STATE,
                name = NAME,
                imageUrl = IMAGE_URL,
                cuisine = CUISINE,
                prepTime = PREP_TIME_UI_STATE,
                cookingTime = COOKING_TIME_UI_STATE,
                difficulty = DIFFICULTY,
                caloriesPerServing = CALORIES_PER_SERVING_UI_STATE
            )
        )
    )
}
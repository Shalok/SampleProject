package com.sample.presentation.feature.recipedetails.mapper

import com.sample.domain.recipedetail.entities.Recipe
import com.sample.presentation.feature.recipedetails.uistate.RecipeDetailUiState
import org.junit.Assert
import org.junit.Test

private const val ID = 1
private const val NAME = "Recipe"
private const val IMAGE = "https://imgurl.com"
private const val CUISINE = "Indian"
private const val INGREDIENT_SALT = "salt"
private const val INGREDIENT_PEPPER = "pepper"
private const val INGREDIENT_HONEY = "honey"
private const val INSTRUCTION_FIRST = "prepare a bowl"
private const val INSTRUCTION_TWO = "mix the honey with pepper"
private const val PREP_TIME = 10
private const val COOK_TIME = 30
private const val SERVING = 2
private const val DIFFICULTY = "medium"
private const val CALORIE_PER_SERVING = 2
private const val TAG1 = "Spicy"
private const val TAG2 = "Indian"
private const val USER_ID = 1
private const val RATING = 1.0
private const val REVIEW_COUNT = 1
private const val MEAL_TYPE = "Breakfast"
private const val MEAL_TYPE_TWO = "Lunch"

class RecipeUiMapperTest {

    private val testClass = RecipeUiMapper()

    @Test
    fun `GIVEN recipe WHEN map THEN return ui state`() {
        val result = testClass(getSampleRecipeData())
        Assert.assertEquals(getSampleUIState(), result)
    }

    @Test
    fun `GIVEN recipe with null values WHEN map THEN return ui state with empty string`() {
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
        val result = testClass(recipe)
        Assert.assertEquals(
            RecipeDetailUiState.DataLoaded(
                id = "",
                name = "",
                imageUrl = "",
                cuisine = "",
                ingredients = "",
                instructions = emptyList(),
                prepTimeMinutes = 0,
                cookTimeMinutes = "",
                servings = 0,
                difficulty = "",
                caloriesPerServing = 0,
                tags = emptyList(),
                userId = 0,
                rating = 0.0,
                description = ""
            ), result
        )
    }

    private fun getSampleRecipeData() =
        Recipe(
            id = ID,
            name = NAME,
            image = IMAGE,
            cuisine = CUISINE,
            ingredients = listOf(INGREDIENT_SALT, INGREDIENT_PEPPER, INGREDIENT_HONEY),
            instructions = listOf(INSTRUCTION_FIRST, INSTRUCTION_TWO),
            prepTimeMinutes = PREP_TIME,
            cookTimeMinutes = COOK_TIME,
            servings = SERVING,
            difficulty = DIFFICULTY,
            caloriesPerServing = CALORIE_PER_SERVING,
            tags = listOf(TAG1, TAG2),
            userId = USER_ID,
            rating = RATING,
            reviewCount = REVIEW_COUNT,
            mealType = listOf(MEAL_TYPE, MEAL_TYPE_TWO)
        )

    private fun getSampleUIState() =
        RecipeDetailUiState.DataLoaded(
            id = ID.toString(),
            name = NAME,
            imageUrl = IMAGE,
            cuisine = CUISINE,
            ingredients = INGREDIENT_SALT.plus(",").plus(INGREDIENT_PEPPER).plus(",")
                .plus(INGREDIENT_HONEY),
            instructions = listOf(INSTRUCTION_FIRST, INSTRUCTION_TWO),
            prepTimeMinutes = PREP_TIME,
            cookTimeMinutes = COOK_TIME.toString(),
            servings = SERVING,
            difficulty = DIFFICULTY,
            caloriesPerServing = CALORIE_PER_SERVING,
            tags = listOf(TAG1, TAG2),
            userId = USER_ID,
            rating = RATING,
            description = CUISINE,
        )

}
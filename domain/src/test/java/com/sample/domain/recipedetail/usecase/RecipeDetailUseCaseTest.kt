package com.sample.domain.recipedetail.usecase

import com.sample.core.networking.utility.Result
import com.sample.domain.recipedetail.entities.Recipe
import com.sample.domain.recipedetail.repository.RecipeDetailRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class RecipeDetailUseCaseTest {

    private val recipeDetailRepository = mockk<RecipeDetailRepository>()
    private val testClass = RecipeDetailUseCase(recipeDetailRepository)

    @Test
    fun `GIVEN recipe detail data WHEN invoke THEN return success`() = runTest {
        coEvery { recipeDetailRepository.getRecipeDetails(any()) } returns Result.Success(
            getSampleRecipeData()
        )
        val result = testClass(RECIPE_ID_VALUE)
        coVerify(exactly = 1) {
            recipeDetailRepository.getRecipeDetails(any())
        }
        assert(result is Result.Success)
        Assert.assertEquals((result as Result.Success).data, getSampleRecipeData())
    }

    @Test
    fun `GIVEN recipe detail data WHEN invoke THEN return error`() = runTest {
        val throwable = mockk<Throwable>()
        coEvery { recipeDetailRepository.getRecipeDetails(any()) } returns Result.Error(
            throwable
        )
        val result = testClass(RECIPE_ID_VALUE)
        coVerify(exactly = 1) {
            recipeDetailRepository.getRecipeDetails(any())
        }
        assert(result is Result.Error)
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

    private companion object {
        private const val RECIPE_ID_VALUE = "3"
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
    }
}
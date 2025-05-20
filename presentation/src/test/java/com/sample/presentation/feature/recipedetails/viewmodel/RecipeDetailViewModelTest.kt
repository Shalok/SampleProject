package com.sample.presentation.feature.recipedetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.sample.core.networking.utility.Result
import com.sample.domain.recipedetail.entities.Recipe
import com.sample.domain.recipedetail.usecase.RecipeDetailUseCase
import com.sample.presentation.feature.recipedetails.intent.RecipeDetailIntent
import com.sample.presentation.feature.recipedetails.mapper.RecipeUiMapper
import com.sample.presentation.feature.recipedetails.uistate.RecipeDetailUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

private const val RECIPE_ID = "recipeId"
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
private const val ERROR_MSG_IO_EXCEPTION = "IO exception"

class RecipeDetailViewModelTest {

    private val recipeDetailUseCase = mockk<RecipeDetailUseCase>()

    private val stateHandle = mockk<SavedStateHandle>()

    private val recipesUiMapper = mockk<RecipeUiMapper>()

    private val testClass = RecipeDetailViewModel(
        recipeDetailUseCase,
        stateHandle,
        recipesUiMapper
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `GIVEN all recipe page load WHEN before api call THEN recipe detail state is loading `() {
        val result = testClass.recipeDetail.value
        Assert.assertTrue(result is RecipeDetailUiState.LOADING)
    }

    @Test
    fun `GIVEN all recipe page load WHEN handle events THEN invoke use case `() =
        runTest {
            every { recipesUiMapper(getSampleRecipeData()) } returns getSampleUIState()
            coEvery { recipeDetailUseCase(any()) } returns Result.Success(
                getSampleRecipeData()
            )
            every { stateHandle.get<String>(RECIPE_ID) } returns RECIPE_ID_VALUE
            testClass.handleEvents(RecipeDetailIntent.LoadPage)
            coVerify(exactly = 1) {
                recipeDetailUseCase(any())
            }
        }

    @Test
    fun `GIVEN all recipe page load WHEN handle events THEN invoke mapper with success result`() = runTest {
        every { recipesUiMapper(getSampleRecipeData()) } returns getSampleUIState()
        every { stateHandle.get<String>(RECIPE_ID) } returns RECIPE_ID_VALUE
        val recipe = getSampleRecipeData()
        coEvery { recipeDetailUseCase(any()) } returns Result.Success(
            recipe
        )
        testClass.handleEvents(RecipeDetailIntent.LoadPage)
        val result = testClass.recipeDetail.value
        Assert.assertTrue(result is RecipeDetailUiState.DataLoaded)
        verify { recipesUiMapper(any()) }
    }

    @Test
    fun `GIVEN all recipe page load WHEN handle events THEN invoke mapper with error result`() = runTest {
        val throwable = mockk<Throwable>()
        every { stateHandle.get<String>(RECIPE_ID) } returns RECIPE_ID_VALUE
        coEvery { recipeDetailUseCase(any()) } returns Result.Error(
            throwable
        )
        every { throwable.message } returns ERROR_MSG_IO_EXCEPTION
        testClass.handleEvents(RecipeDetailIntent.LoadPage)
        val result = testClass.recipeDetail.value
        Assert.assertTrue(result is RecipeDetailUiState.ErrorState)
        Assert.assertTrue((result as RecipeDetailUiState.ErrorState).errorMessage == ERROR_MSG_IO_EXCEPTION)
        verify(exactly = 0) { recipesUiMapper(any()) }
    }

    @Test
    fun `GIVEN all recipe page load WHEN handle events THEN throw exception without throwable message`() =
        runTest {
            every { stateHandle.get<String>(RECIPE_ID) } returns null
            testClass.handleEvents(RecipeDetailIntent.LoadPage)
            val result = testClass.recipeDetail.value
            Assert.assertTrue(result is RecipeDetailUiState.LOADING)
            coVerify(exactly = 0) { recipeDetailUseCase(any()) }
        }

    @Test
    fun `WHEN all recipe page load THEN invoke mapper with error result`() = runTest {
        val throwable = mockk<Throwable>()
        every { stateHandle.get<String>(RECIPE_ID) } returns RECIPE_ID_VALUE
        coEvery { recipeDetailUseCase(any()) } returns Result.Error(
            throwable
        )
        every { throwable.message } returns null
        testClass.handleEvents(RecipeDetailIntent.LoadPage)
        val result = testClass.recipeDetail.value
        Assert.assertTrue(result is RecipeDetailUiState.ErrorState)
        Assert.assertNull((result as RecipeDetailUiState.ErrorState).errorMessage)
        verify(exactly = 0) { recipesUiMapper(any()) }
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
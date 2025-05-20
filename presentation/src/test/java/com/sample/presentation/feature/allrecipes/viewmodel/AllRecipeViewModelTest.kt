package com.sample.presentation.feature.allrecipes.viewmodel

import com.sample.core.networking.utility.Result
import com.sample.domain.allrecipes.entities.AllRecipes
import com.sample.domain.allrecipes.usecase.AllRecipesUseCase
import com.sample.domain.recipedetail.entities.Recipe
import com.sample.presentation.feature.allrecipes.intent.AllRecipesIntent
import com.sample.presentation.feature.allrecipes.mapper.AllRecipesUiMapper
import com.sample.presentation.feature.allrecipes.uistate.AllRecipesUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class AllRecipeViewModelTest {

    private val getAllRecipeUseCase = mockk<AllRecipesUseCase>()

    private val allRecipesUiMapper = mockk<AllRecipesUiMapper>()

    private val testClass = AllRecipeViewModel(getAllRecipeUseCase, allRecipesUiMapper)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `GIVEN initialisation WHEN load page THEN return loading`() {
        val result = testClass.allRecipes.value
        assert(result is AllRecipesUiState.LOADING)
    }

    @Test
    fun `GIVEN happy flow WHEN load page THEN return success`() = runTest {
        val recipe = getSampleRecipeData()
        coEvery { getAllRecipeUseCase() } returns Result.Success(
            recipe
        )
        coEvery {
            allRecipesUiMapper(recipe)
        } returns getUiStateSuccessData()
        testClass.handleEvent(AllRecipesIntent.LoadPage)
        val result = testClass.allRecipes.value
        assert(result is AllRecipesUiState.DataLoadedUiState)
        verify { allRecipesUiMapper(eq(recipe)) }
    }

    @Test
    fun `GIVEN happy flow WHEN load page THEN return error`() = runTest {
        val throwable = mockk<Throwable>()
        coEvery { getAllRecipeUseCase() } returns Result.Error(
            throwable
        )
        every { throwable.message } returns IO_EXCEPTION
        testClass.handleEvent(AllRecipesIntent.LoadPage)
        val result = testClass.allRecipes.value
        assert(result is AllRecipesUiState.ErrorUiState)
        assert((result as AllRecipesUiState.ErrorUiState).errorMessage == IO_EXCEPTION)
        verify(exactly = 0) { allRecipesUiMapper(any()) }
    }

    @Test
    fun `GIVEN happy flow WHEN load page THEN return error without throwable message`() = runTest {
        val throwable = mockk<Throwable>()
        coEvery { getAllRecipeUseCase() } returns Result.Error(
            throwable
        )
        every { throwable.message } returns null
        testClass.handleEvent(AllRecipesIntent.LoadPage)
        val result = testClass.allRecipes.value
        assert(result is AllRecipesUiState.ErrorUiState)
        assertNull((result as AllRecipesUiState.ErrorUiState).errorMessage)
        verify(exactly = 0) { allRecipesUiMapper(any()) }
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

    private companion object {
        private const val IO_EXCEPTION = "IO exception"
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
    }
}
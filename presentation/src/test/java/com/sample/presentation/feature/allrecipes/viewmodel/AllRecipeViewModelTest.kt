package com.sample.presentation.feature.allrecipes.viewmodel

import com.sample.core.networking.Result
import com.sample.domain.allrecipes.usecase.AllRecipesUseCase
import com.sample.domain.allrecipes.entities.AllRecipes
import com.sample.domain.recipedetail.entities.Recipe
import com.sample.presentation.feature.allrecipes.intent.AllRecipesIntent
import com.sample.presentation.feature.allrecipes.mapper.AllRecipesUiMapper
import com.sample.presentation.feature.allrecipes.uistate.AllRecipesUiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AllRecipeViewModelTest {

    @RelaxedMockK
    lateinit var getAllRecipeUseCase: AllRecipesUseCase

    @RelaxedMockK
    lateinit var throwable: Throwable

    @SpyK
    var allRecipesUiMapper = AllRecipesUiMapper()

    private lateinit var testClass: AllRecipeViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        testClass = AllRecipeViewModel(
            getAllRecipeUseCase,
            allRecipesUiMapper,
            testDispatcher
        )
    }

    @Test
    fun defaultUiStateTest(){
        val result = testClass.allRecipes.value
        assert(result is AllRecipesUiState.LOADING)
    }

    @Test
    fun useCaseInvocation() =
        runTest {
            testClass.handleEvent(AllRecipesIntent.LoadPage)
            coVerify(exactly = 1) {
                getAllRecipeUseCase.invoke()
            }
        }

    @Test
    fun allRecipesSuccess() = runTest {
        val recipe = getSampleRecipeData()
            coEvery { getAllRecipeUseCase.invoke() } returns Result.Success(
                recipe
            )
        testClass.handleEvent(AllRecipesIntent.LoadPage)
        val result = testClass.allRecipes.value
        assert(result is AllRecipesUiState.DataLoadedUiState)
        verify { allRecipesUiMapper.invoke(any()) }
    }

    @Test
    fun allRecipesFailure() = runTest {
        coEvery { getAllRecipeUseCase.invoke() } returns Result.Error(
            throwable
        )
        every { throwable.message } returns "IO exception"
        testClass.handleEvent(AllRecipesIntent.LoadPage)
        val result = testClass.allRecipes.value
        assert(result is AllRecipesUiState.ErrorUiState)
        assert((result as AllRecipesUiState.ErrorUiState).errorMessage == "IO exception")
        verify(exactly = 0) { allRecipesUiMapper.invoke(any()) }
    }

    @Test
    fun allRecipesFailureWithNull() = runTest {
        coEvery { getAllRecipeUseCase.invoke() } returns Result.Error(
            throwable
        )
        every { throwable.message } returns null
        testClass.handleEvent(AllRecipesIntent.LoadPage)
        val result = testClass.allRecipes.value
        assert(result is AllRecipesUiState.ErrorUiState)
        assert((result as AllRecipesUiState.ErrorUiState).errorMessage == "Something went wrong")
        verify(exactly = 0) { allRecipesUiMapper.invoke(any()) }
    }

    @Test
    fun allRecipesFailureWithThrowableNull() = runTest {
        coEvery { getAllRecipeUseCase.invoke() } returns Result.Error(
            null
        )
        testClass.handleEvent(AllRecipesIntent.LoadPage)
        val result = testClass.allRecipes.value
        assert(result is AllRecipesUiState.ErrorUiState)
        assert((result as AllRecipesUiState.ErrorUiState).errorMessage == "Something went wrong")
        verify(exactly = 0) { allRecipesUiMapper.invoke(any()) }
    }

    private fun getSampleRecipeData() = AllRecipes(
        total = 1,
        recipes = listOf(
            Recipe(
                id = 1,
                name = "name",
                image = "image",
                cuisine = "cuisine"
            )
        )
    )

}
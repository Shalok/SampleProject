package com.sample.presentation.feature.recipedetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.sample.core.networking.Result
import com.sample.domain.recipedetail.entities.Recipe
import com.sample.domain.recipedetail.usecase.RecipeDetailUseCase
import com.sample.presentation.feature.recipedetails.intent.RecipeDetailIntent
import com.sample.presentation.feature.recipedetails.mapper.RecipeUiMapper
import com.sample.presentation.feature.recipedetails.uistate.RecipeDetailUiState
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
class RecipeDetailViewModelTest {

    @RelaxedMockK
    lateinit var recipeDetailUseCase: RecipeDetailUseCase

    @RelaxedMockK
    lateinit var stateHandle: SavedStateHandle

    @RelaxedMockK
    lateinit var throwable: Throwable

    @SpyK
    var recipesUiMapper = RecipeUiMapper()

    private lateinit var testClass: RecipeDetailViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        testClass = RecipeDetailViewModel(
            recipeDetailUseCase,
            stateHandle,
            recipesUiMapper,
            testDispatcher
        )
    }

    @Test
    fun defaultUiStateTest(){
        val result = testClass.recipeDetail.value
        assert(result is RecipeDetailUiState.LOADING)
    }

    @Test
    fun useCaseInvocation() =
        runTest {
            every { stateHandle.get<String>("recipeId") } returns "3"
            testClass.handleEvents(RecipeDetailIntent.LoadPage)
            coVerify(exactly = 1) {
                recipeDetailUseCase.invoke(any())
            }
        }

    @Test
    fun allRecipesSuccess() = runTest {
        every { stateHandle.get<String>("recipeId") } returns "3"
        val recipe = getSampleRecipeData()
        coEvery { recipeDetailUseCase.invoke(any()) } returns Result.Success(
            recipe
        )
        testClass.handleEvents(RecipeDetailIntent.LoadPage)
        val result = testClass.recipeDetail.value
        assert(result is RecipeDetailUiState.DataLoaded)
        verify { recipesUiMapper.invoke(any()) }
    }

    @Test
    fun allRecipesFailure() = runTest {
        every { stateHandle.get<String>("recipeId") } returns "3"
        coEvery { recipeDetailUseCase.invoke(any()) } returns Result.Error(
            throwable
        )
        every { throwable.message } returns "IO exception"
        testClass.handleEvents(RecipeDetailIntent.LoadPage)
        val result = testClass.recipeDetail.value
        assert(result is RecipeDetailUiState.ErrorState)
        assert((result as RecipeDetailUiState.ErrorState).errorMessage == "IO exception")
        verify(exactly = 0) { recipesUiMapper.invoke(any()) }
    }

    @Test
    fun stateHandleNullTest() = runTest {
        every { stateHandle.get<String>("recipeId") } returns null
        testClass.handleEvents(RecipeDetailIntent.LoadPage)
        val result = testClass.recipeDetail.value
        assert(result is RecipeDetailUiState.LOADING)
        coVerify (exactly = 0){ recipeDetailUseCase.invoke(any()) }
    }

    @Test
    fun allRecipesFailureWithNull() = runTest {
        every { stateHandle.get<String>("recipeId") } returns "3"
        coEvery { recipeDetailUseCase.invoke(any()) } returns Result.Error(
            throwable
        )
        every { throwable.message } returns null
        testClass.handleEvents(RecipeDetailIntent.LoadPage)
        val result = testClass.recipeDetail.value
        assert(result is RecipeDetailUiState.ErrorState)
        assert((result as RecipeDetailUiState.ErrorState).errorMessage == "Unknown Error")
        verify(exactly = 0) { recipesUiMapper.invoke(any()) }
    }

    @Test
    fun allRecipesFailureWithThrowableNull() = runTest {
        every { stateHandle.get<String>("recipeId") } returns "3"
        coEvery { recipeDetailUseCase.invoke(any()) } returns Result.Error(
            null
        )
        testClass.handleEvents(RecipeDetailIntent.LoadPage)
        val result = testClass.recipeDetail.value
        assert(result is RecipeDetailUiState.ErrorState)
        assert((result as RecipeDetailUiState.ErrorState).errorMessage == "Unknown Error")
        verify(exactly = 0) { recipesUiMapper.invoke(any()) }
    }

    private fun getSampleRecipeData() =
            Recipe(
                id = 1,
                name = "name",
                image = "image",
                cuisine = "cuisine"
            )
}
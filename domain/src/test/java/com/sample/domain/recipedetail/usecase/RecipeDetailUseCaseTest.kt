package com.sample.domain.recipedetail.usecase

import com.sample.core.networking.Result
import com.sample.domain.recipedetail.entities.Recipe
import com.sample.domain.recipedetail.repository.RecipeDetailRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeDetailUseCaseTest {

    @MockK
    private lateinit var recipeDetailRepository: RecipeDetailRepository
    private lateinit var testClass: RecipeDetailUseCase

    private val dispatcher = UnconfinedTestDispatcher()

    @MockK
    private lateinit var throwable: Throwable

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        testClass = RecipeDetailUseCase(recipeDetailRepository)
    }

    @Test
    fun invokeTestSuccess() = runTest {
        coEvery { recipeDetailRepository.getRecipeDetails(any()) } returns Result.Success(
            Recipe(
                id = 1,
                name = "name",
                ingredients = listOf("ingredient"),
                instructions = listOf("instruction")
            )
        )
        val result = testClass.invoke("1")
        coVerify(exactly = 1) {
            recipeDetailRepository.getRecipeDetails(any())
        }
        assert(result is Result.Success)
        Assert.assertEquals((result as Result.Success).data.id, 1)
        Assert.assertEquals(result.data.name, "name")
        Assert.assertEquals(result.data.ingredients.size, 1)
        Assert.assertEquals(result.data.ingredients[0], "ingredient")
        Assert.assertEquals(result.data.instructions.size, 1)
        Assert.assertEquals(result.data.instructions[0], "instruction")
    }

    @Test
    fun invokeTestError() = runTest {
        coEvery { recipeDetailRepository.getRecipeDetails(any()) } returns Result.Error(
            throwable
        )
        val result = testClass.invoke("1")
        coVerify(exactly = 1) {
            recipeDetailRepository.getRecipeDetails(any())
        }
        assert(result is Result.Error)
    }
}
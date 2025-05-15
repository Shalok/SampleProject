package com.sample.domain.allrecipes.usecase

import com.sample.core.networking.Result
import com.sample.domain.allrecipes.entities.AllRecipes
import com.sample.domain.recipedetail.entities.Recipe
import com.sample.domain.allrecipes.repository.AllRecipesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AllRecipesUseCaseTest {

    @RelaxedMockK
    private lateinit var allRecipesRepository: AllRecipesRepository


    @RelaxedMockK
    private lateinit var throwable: Throwable

    private lateinit var testClass: AllRecipesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        testClass = AllRecipesUseCase(allRecipesRepository)
    }

    @Test
    fun invokeTestSuccess() = runTest {
        coEvery { allRecipesRepository.getAllRecipes() } returns
                Result.Success(
                    AllRecipes(
                        recipes = listOf(
                            Recipe(
                                id = 1,
                                name = "name",
                                ingredients = listOf("ingredient"),
                                instructions = listOf("instruction"),
                            )
                        ),
                        total = 30
                    )
                )
        val result = testClass.invoke()
        assert(result is Result.Success)
        coVerify(exactly = 1) {
            allRecipesRepository.getAllRecipes()
        }
        assert((result as Result.Success).data.recipes.size == 1)
        assert(result.data.recipes[0].id == 1)
        assert(result.data.recipes[0].name == "name")
        assert(result.data.recipes[0].ingredients.size == 1)
        assert(result.data.recipes[0].ingredients[0] == "ingredient")
        assert(result.data.recipes[0].instructions.size == 1)
        assert(result.data.recipes[0].instructions[0] == "instruction")
    }

    @Test
    fun invokeTestError() = runTest {
        coEvery { allRecipesRepository.getAllRecipes() } returns Result.Error(
            throwable
        )
        val result = testClass.invoke()
        assert(result is Result.Error)
        coVerify(exactly = 1) {
            allRecipesRepository.getAllRecipes()
        }
    }
}
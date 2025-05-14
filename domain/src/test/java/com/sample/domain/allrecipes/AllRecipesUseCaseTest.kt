package com.sample.domain.allrecipes

import com.sample.core.networking.Result
import com.sample.data.allrecipes.AllRecipesRepository
import com.sample.data.allrecipes.entity.AllRecipesDto
import com.sample.data.recipedetail.entity.RecipesDto
import com.sample.domain.mapper.AllRecipesDtoMapper
import com.sample.domain.mapper.RecipeDtoMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import io.mockk.spyk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AllRecipesUseCaseTest {

    @RelaxedMockK
    private lateinit var allRecipesRepository: AllRecipesRepository
    private val allRecipesDtoMapper = spyk(AllRecipesDtoMapper(spyk(RecipeDtoMapper())))


    @RelaxedMockK
    private lateinit var throwable: Throwable

    private lateinit var testClass: AllRecipesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        testClass = AllRecipesUseCase(allRecipesRepository,allRecipesDtoMapper)
    }

    @Test
    fun invokeTestSuccess() = runTest {
        val captureDto = slot<AllRecipesDto>()
        coEvery { allRecipesDtoMapper.invoke(capture(captureDto)) } answers {
            callOriginal()
        }
        coEvery { allRecipesRepository.getAllRecipes() } returns
                Result.Success(
                    AllRecipesDto(
                        recipes = listOf(
                            RecipesDto(
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
        coVerify (exactly = 1){
            allRecipesDtoMapper.invoke(any())
        }
        assertNotNull(captureDto.captured)
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
        coVerify (exactly = 0){
            allRecipesDtoMapper.invoke(any())
        }
    }
}
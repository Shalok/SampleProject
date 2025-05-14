package com.sample.domain.recipedetail

import com.sample.core.networking.Result
import com.sample.data.recipedetail.RecipeDetailRepository
import com.sample.data.recipedetail.entity.RecipesDto
import com.sample.domain.mapper.RecipeDtoMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import io.mockk.spyk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeDetailUseCaseTest {

    @RelaxedMockK
    private lateinit var recipeDetailRepository : RecipeDetailRepository
    private lateinit var testClass: RecipeDetailUseCase
    private val allRecipesDtoMapper = spyk(RecipeDtoMapper())

    private val dispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    private lateinit var throwable : Throwable

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        testClass = RecipeDetailUseCase(recipeDetailRepository,allRecipesDtoMapper)
    }

    @Test
    fun invokeTestSuccess() = runTest {
        val captureDto = slot<RecipesDto>()
        coEvery { allRecipesDtoMapper.invoke(capture(captureDto)) } answers {
            callOriginal()
        }
        coEvery { recipeDetailRepository.getRecipeDetails(any()) } returns  Result.Success(
            RecipesDto(
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
        assert((result as Result.Success).data.id == 1)
        assert(result.data.name == "name")
        assert(result.data.ingredients.size == 1)
        assert(result.data.ingredients[0] == "ingredient")
        assert(result.data.instructions.size == 1)
        assert(result.data.instructions[0] == "instruction")
        coVerify (exactly = 1){
            allRecipesDtoMapper.invoke(any())
        }
        assertNotNull(captureDto.captured)
    }

    @Test
    fun invokeTestError() = runTest {
        coEvery { recipeDetailRepository.getRecipeDetails(any()) } returns  Result.Error(
            throwable
        )
        val result = testClass.invoke("1")
        coVerify(exactly = 1) {
            recipeDetailRepository.getRecipeDetails(any())
        }
        assert(result is Result.Error)
        coVerify (exactly = 0){
            allRecipesDtoMapper.invoke(any())
        }
    }
}
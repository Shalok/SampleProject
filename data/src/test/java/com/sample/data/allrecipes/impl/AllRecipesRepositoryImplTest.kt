package com.sample.data.allrecipes.impl

import com.sample.core.networking.Result
import com.sample.data.allrecipes.dtos.AllRecipesDto
import com.sample.data.mapper.AllRecipesDtoMapper
import com.sample.data.mapper.RecipeDtoMapper
import com.sample.data.recipedetail.entity.RecipesDto
import com.sample.data.services.RecipesApiServices
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AllRecipesRepositoryImplTest {

    @MockK
    private lateinit var recipesApiServices: RecipesApiServices

    @MockK
    private lateinit var runtimeException: RuntimeException
    private val allRecipesDtoMapper = spyk(AllRecipesDtoMapper(spyk(RecipeDtoMapper())))


    private lateinit var testClass: AllRecipesRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        testClass = AllRecipesRepositoryImpl(recipesApiServices, allRecipesDtoMapper)
    }

    @Test
    fun getAllRecipesSuccessTest() = runTest {
        val captureDto = slot<AllRecipesDto>()
        coEvery { allRecipesDtoMapper.invoke(capture(captureDto)) } answers {
            callOriginal()
        }
        coEvery { recipesApiServices.getAllRecipes() } returns Response.success(
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
        val result = testClass.getAllRecipes()
        assert(result is Result.Success)
        assert((result as Result.Success).data.recipes.size == 1)
        coVerify(exactly = 1) {
            allRecipesDtoMapper.invoke(any())
        }
        assertNotNull(captureDto.captured)
    }

    @Test
    fun getAllRecipesSuccessWithEmptyBodyTest() = runTest {
        coEvery { recipesApiServices.getAllRecipes() } returns Response.success(
            null
        )
        val result = testClass.getAllRecipes()
        assert(result is Result.Error)
        coVerify(exactly = 0) {
            allRecipesDtoMapper.invoke(any())
        }
    }

    @Test
    fun getAllRecipesSuccessWithIsNotSuccessTest() = runTest {
        coEvery { recipesApiServices.getAllRecipes() } returns Response.error(
            400,
            mockk(relaxed = true)
        )
        val result = testClass.getAllRecipes()
        assert(result is Result.Error)
        coVerify(exactly = 0) {
            allRecipesDtoMapper.invoke(any())
        }
    }

    @Test
    fun getAllRecipesErrorCaughtTest() = runTest {
        coEvery { recipesApiServices.getAllRecipes() } throws runtimeException
        val result = testClass.getAllRecipes()
        assert(result is Result.Error)
        coVerify(exactly = 0) {
            allRecipesDtoMapper.invoke(any())
        }
    }

    @Test
    fun getAllRecipesErrorUncaughtTest() = runTest {
        val throwable = mockk<Throwable>()
        coEvery { recipesApiServices.getAllRecipes() } throws throwable
        val result = testClass.getAllRecipes()
        assert(result is Result.Error)
        coVerify(exactly = 0) {
            allRecipesDtoMapper.invoke(any())
        }
    }
}
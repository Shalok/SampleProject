package com.sample.data.recipedetail.impl

import com.sample.core.networking.Result
import com.sample.data.recipedetail.entity.RecipesDto
import com.sample.data.services.RecipesApiServices
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RecipeDetailRepositoryImplTest {
    @RelaxedMockK
    private lateinit var recipesApiServices: RecipesApiServices
    @RelaxedMockK
    private lateinit var runtimeException: RuntimeException
    private val allRecipesDtoMapper = spyk(com.sample.data.mapper.RecipeDtoMapper())




    private lateinit var testClass: RecipeDetailRepositoryImpl

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        testClass = RecipeDetailRepositoryImpl(recipesApiServices,allRecipesDtoMapper)
    }

    @Test
    fun getAllRecipesSuccessTest()= runTest {
        val captureDto = slot<RecipesDto>()
        coEvery { allRecipesDtoMapper.invoke(capture(captureDto)) } answers {
            callOriginal()
        }
        coEvery { recipesApiServices.getRecipeDetail(any()) } returns Response.success(
                    RecipesDto(
                        id = 1,
                        name = "name",
                        ingredients = listOf("ingredient"),
                        instructions = listOf("instruction")
                    )
        )
        val result = testClass.getRecipeDetails("3")
        assert(result is Result.Success)
        coVerify (exactly = 1){
            allRecipesDtoMapper.invoke(any())
        }
        assertNotNull(captureDto.captured)
    }

    @Test
    fun getAllRecipesSuccessWithEmptyBodyTest()= runTest {
        coEvery { recipesApiServices.getRecipeDetail(any()) } returns Response.success(
            null
        )
        val result = testClass.getRecipeDetails("3")
        assert(result is Result.Error)
        coVerify (exactly = 0){
            allRecipesDtoMapper.invoke(any())
        }
    }

    @Test
    fun getAllRecipesSuccessWithIsNotSuccessTest()= runTest {
        coEvery { recipesApiServices.getRecipeDetail(any()) } returns Response.error(
            400,
            mockk(relaxed = true)
        )
        val result = testClass.getRecipeDetails("3")
        assert(result is Result.Error)
        coVerify (exactly = 0){
            allRecipesDtoMapper.invoke(any())
        }
    }

    @Test
    fun getAllRecipesErrorCaughtTest()= runTest {
        coEvery { recipesApiServices.getRecipeDetail(any()) } throws runtimeException
        val result = testClass.getRecipeDetails("3")
        assert(result is Result.Error)
        coVerify (exactly = 0){
            allRecipesDtoMapper.invoke(any())
        }
    }

    @Test
    fun getAllRecipesErrorUncaughtTest()= runTest {
        val throwable = mockk<Throwable>(relaxed = true)
        coEvery { recipesApiServices.getRecipeDetail(any()) } throws throwable
        val result = testClass.getRecipeDetails("3")
        assert(result is Result.Error)
        coVerify (exactly = 0){
            allRecipesDtoMapper.invoke(any())
        }
    }
}
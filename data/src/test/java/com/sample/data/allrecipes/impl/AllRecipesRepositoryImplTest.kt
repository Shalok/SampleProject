package com.sample.data.allrecipes.impl

import com.sample.data.allrecipes.entity.AllRecipesDto
import com.sample.data.recipedetail.entity.RecipesDto
import com.sample.data.services.RecipesApiServices
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import  com.sample.core.networking.Result
import io.mockk.mockk

class AllRecipesRepositoryImplTest {

    @RelaxedMockK
    private lateinit var recipesApiServices: RecipesApiServices
    @RelaxedMockK
    private lateinit var runtimeException: RuntimeException

    private lateinit var testClass: AllRecipesRepositoryImpl

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        testClass = AllRecipesRepositoryImpl(recipesApiServices)
    }

    @Test
    fun getAllRecipesSuccessTest()= runTest {

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
    }

    @Test
    fun getAllRecipesSuccessWithEmptyBodyTest()= runTest {
        coEvery { recipesApiServices.getAllRecipes() } returns Response.success(
            null
        )
        val result = testClass.getAllRecipes()
        assert(result is Result.Error)
    }

    @Test
    fun getAllRecipesSuccessWithIsNotSuccessTest()= runTest {
        coEvery { recipesApiServices.getAllRecipes() } returns Response.error(
            400,
            mockk(relaxed = true)
        )
        val result = testClass.getAllRecipes()
        assert(result is Result.Error)
    }

    @Test
    fun getAllRecipesErrorCaughtTest()= runTest {
        coEvery { recipesApiServices.getAllRecipes() } throws runtimeException
        val result = testClass.getAllRecipes()
        assert(result is Result.Error)
    }

    @Test
    fun getAllRecipesErrorUncaughtTest()= runTest {
        val throwable = mockk<Throwable>(relaxed = true)
        coEvery { recipesApiServices.getAllRecipes() } throws throwable
        val result = testClass.getAllRecipes()
        assert(result is Result.Error)
    }
}
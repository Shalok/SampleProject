package com.sample.data.recipedetail.impl

import com.sample.core.networking.network.impl.ApiExecutorImpl
import com.sample.core.networking.utility.Result
import com.sample.data.mapper.RecipeDtoMapper
import com.sample.data.recipedetail.dto.RecipesDto
import com.sample.data.services.RecipesApiServices
import com.sample.domain.recipedetail.entities.Recipe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

private const val RECIPE_ID = "30"
private const val ID = 1
private const val NAME = "Recipe"
private const val IMAGE = "https://imgurl.com"
private const val CUISINE = "Indian"
private const val INGREDIENT_SALT = "salt"
private const val INGREDIENT_PEPPER = "pepper"
private const val INGREDIENT_HONEY = "honey"
private const val INSTRUCTION_FIRST = "prepare a bowl"
private const val INSTRUCTION_TWO = "mix the honey with pepper"
private const val PREP_TIME = 10
private const val COOK_TIME = 30
private const val SERVING = 2
private const val DIFFICULTY = "medium"
private const val CALORIE_PER_SERVING = 2
private const val TAG1 = "Spicy"
private const val TAG2 = "Indian"
private const val USER_ID = 1
private const val RATING = 1.0
private const val REVIEW_COUNT = 1
private const val MEAL_TYPE = "Breakfast"
private const val MEAL_TYPE_TWO = "Lunch"
private const val ERROR_MSG_UNCAUGHT_EXCEPTION = "Uncaught exception"
private const val ERROR_MSG = "Error"
private const val ERROR_MSG_BODY_NULL = "Response body is null"

class RecipeDetailRepositoryImplTest {

    private val recipesApiServices = mockk<RecipesApiServices>()

    private val allRecipesDtoMapper = mockk<RecipeDtoMapper>()

    private val apiExecutor = ApiExecutorImpl()

    private val testClass = RecipeDetailRepositoryImpl(
        recipesApiServices,
        allRecipesDtoMapper,
        apiExecutor
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN happy path WHEN call getRecipeDetails THEN return success`() = runTest {
        val captureDto = slot<RecipesDto>()
        val responseMock = mockk<Response<RecipesDto>>()
        coEvery {
            recipesApiServices.getRecipeDetail(RECIPE_ID)
        } returns responseMock

        coEvery {
            responseMock.isSuccessful
        } returns true

        coEvery {
            responseMock.body()
        } returns provideRecipeDtoData()

        coEvery { allRecipesDtoMapper(capture(captureDto)) } answers {
            provideRecipeData()
        }
        coEvery { recipesApiServices.getRecipeDetail(any()) } returns Response.success(
            provideRecipeDtoData()
        )
        val result = testClass.getRecipeDetails(RECIPE_ID)
        assertEquals(provideRecipeData(), (result as Result.Success).data)
        coVerify(exactly = 1) {
            allRecipesDtoMapper(any())
        }
        assertEquals(provideRecipeDtoData(), captureDto.captured)
    }

    @Test
    fun `GIVEN happy path WHEN call getRecipeDetails THEN return empty body response`() = runTest {
        val responseMock = mockk<Response<RecipesDto>>()
        coEvery {
            recipesApiServices.getRecipeDetail(RECIPE_ID)
        } returns responseMock

        coEvery {
            responseMock.isSuccessful
        } returns true

        coEvery {
            responseMock.body()
        } returns null

        val result = testClass.getRecipeDetails(RECIPE_ID)
        assertEquals(ERROR_MSG_BODY_NULL, (result as Result.Error).throwable.message)
        coVerify(exactly = 0) {
            allRecipesDtoMapper(any())
        }
    }

    @Test
    fun `GIVEN error path WHEN call getRecipeDetails THEN return error`() = runTest {
        val responseMock = mockk<RuntimeException>()
        coEvery {
            recipesApiServices.getRecipeDetail(RECIPE_ID)
        } throws responseMock

        coEvery {
            responseMock.message
        } returns ERROR_MSG

        val result = testClass.getRecipeDetails(RECIPE_ID)
        assertEquals(ERROR_MSG, (result as Result.Error).throwable.message)
        coVerify(exactly = 0) {
            allRecipesDtoMapper(any())
        }
    }

    @Test
    fun `GIVEN error path WHEN call getRecipeDetails THEN return Uncaught exception`() = runTest {
        val runtimeException = mockk<Exception>()
        coEvery {
            recipesApiServices.getRecipeDetail(RECIPE_ID)
        } throws runtimeException

        coEvery {
            runtimeException.message
        } returns ERROR_MSG_UNCAUGHT_EXCEPTION
        val result = testClass.getRecipeDetails(RECIPE_ID)
        assertEquals(ERROR_MSG_UNCAUGHT_EXCEPTION, (result as Result.Error).throwable.message)
        coVerify(exactly = 0) {
            allRecipesDtoMapper(any())
        }
    }

    private fun provideRecipeDtoData() =
        RecipesDto(
            id = ID,
            name = NAME,
            image = IMAGE,
            cuisine = CUISINE,
            ingredients = listOf(INGREDIENT_SALT, INGREDIENT_PEPPER, INGREDIENT_HONEY),
            instructions = listOf(INSTRUCTION_FIRST, INSTRUCTION_TWO),
            prepTimeMinutes = PREP_TIME,
            cookTimeMinutes = COOK_TIME,
            servings = SERVING,
            difficulty = DIFFICULTY,
            caloriesPerServing = CALORIE_PER_SERVING,
            tags = listOf(TAG1, TAG2),
            userId = USER_ID,
            rating = RATING,
            reviewCount = REVIEW_COUNT,
            mealType = listOf(MEAL_TYPE, MEAL_TYPE_TWO)
        )

    private fun provideRecipeData() =
        Recipe(
            id = ID,
            name = NAME,
            image = IMAGE,
            cuisine = CUISINE,
            ingredients = listOf(INGREDIENT_SALT, INGREDIENT_PEPPER, INGREDIENT_HONEY),
            instructions = listOf(INSTRUCTION_FIRST, INSTRUCTION_TWO),
            prepTimeMinutes = PREP_TIME,
            cookTimeMinutes = COOK_TIME,
            servings = SERVING,
            difficulty = DIFFICULTY,
            caloriesPerServing = CALORIE_PER_SERVING,
            tags = listOf(TAG1, TAG2),
            userId = USER_ID,
            rating = RATING,
            reviewCount = REVIEW_COUNT,
            mealType = listOf(MEAL_TYPE, MEAL_TYPE_TWO)
        )
}
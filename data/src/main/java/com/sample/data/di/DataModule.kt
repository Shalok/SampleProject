package com.sample.data.di

import com.sample.domain.allrecipes.repository.AllRecipesRepository
import com.sample.data.allrecipes.impl.AllRecipesRepositoryImpl
import com.sample.data.mapper.AllRecipesDtoMapper
import com.sample.data.mapper.RecipeDtoMapper
import com.sample.domain.recipedetail.repository.RecipeDetailRepository
import com.sample.data.recipedetail.impl.RecipeDetailRepositoryImpl
import com.sample.data.services.RecipesApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    /*@Binds
    internal abstract fun bindAllRecipesRepository(
        allRecipesRepositoryImpl: AllRecipesRepositoryImpl
    ): AllRecipesRepository

    @Binds
    internal abstract fun bindRecipeDetailRepository(
        recipeDetailRepositoryImpl: RecipeDetailRepositoryImpl
    ): RecipeDetailRepository*/

    companion object {
        @Provides
        fun provideRecipesApiService(retrofit: Retrofit): RecipesApiServices {
            return retrofit.create(RecipesApiServices::class.java)
        }

        @Provides
        fun provideAllRecipesRepository(
            recipesApiServices: RecipesApiServices,
            allRecipesDtoMapper: AllRecipesDtoMapper
        ): AllRecipesRepository {
            return AllRecipesRepositoryImpl(
                recipesApiServices,
                allRecipesDtoMapper
            )
        }

        @Provides
        fun provideRecipeDetailRepository(
            recipesApiServices: RecipesApiServices,
            recipeDtoMapper: RecipeDtoMapper
            ): RecipeDetailRepository {
            return RecipeDetailRepositoryImpl(
                recipesApiServices,
                recipeDtoMapper
            )
        }

    }
}
package com.sample.data.di

import com.sample.data.allrecipes.AllRecipesRepository
import com.sample.data.allrecipes.impl.AllRecipesRepositoryImpl
import com.sample.data.recipedetail.RecipeDetailRepository
import com.sample.data.recipedetail.impl.RecipeDetailRepositoryImpl
import com.sample.data.services.RecipesApiServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindAllRecipesRepository(
        allRecipesRepositoryImpl: AllRecipesRepositoryImpl
    ): AllRecipesRepository

    @Binds
    internal abstract fun bindRecipeDetailRepository(
        recipeDetailRepositoryImpl: RecipeDetailRepositoryImpl
    ): RecipeDetailRepository

    companion object {
        @Provides
        fun provideRecipesApiService(retrofit: Retrofit): RecipesApiServices {
            return retrofit.create(RecipesApiServices::class.java)
        }

    }
}
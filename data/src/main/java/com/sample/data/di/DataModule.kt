package com.sample.data.di

import com.sample.data.allrecipes.impl.AllRecipesRepositoryImpl
import com.sample.data.mapper.AllRecipesDtoMapper
import com.sample.data.mapper.RecipeDtoMapper
import com.sample.core.networking.network.contract.ApiExecutor
import com.sample.core.networking.network.impl.ApiExecutorImpl
import com.sample.data.recipedetail.impl.RecipeDetailRepositoryImpl
import com.sample.data.services.RecipesApiServices
import com.sample.domain.allrecipes.repository.AllRecipesRepository
import com.sample.domain.recipedetail.repository.RecipeDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {
    @Provides
    fun provideRecipesApiService(retrofit: Retrofit): RecipesApiServices {
        return retrofit.create(RecipesApiServices::class.java)
    }

    @Provides
    fun provideAllRecipesRepository(
        recipesApiServices: RecipesApiServices,
        allRecipesDtoMapper: AllRecipesDtoMapper,
        apiExecutor: ApiExecutor
    ): AllRecipesRepository {
        return AllRecipesRepositoryImpl(
            recipesApiServices,
            allRecipesDtoMapper,
            apiExecutor
        )
    }

    @Provides
    fun provideRecipeDetailRepository(
        recipesApiServices: RecipesApiServices,
        recipeDtoMapper: RecipeDtoMapper,
        apiExecutor: ApiExecutor
    ): RecipeDetailRepository {
        return RecipeDetailRepositoryImpl(
            recipesApiServices,
            recipeDtoMapper,
            apiExecutor
        )
    }


    @Provides
    fun provideApiExecutor(): ApiExecutor {
        return ApiExecutorImpl()
    }

}
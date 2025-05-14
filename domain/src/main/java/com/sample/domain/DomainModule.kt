package com.sample.domain

import com.sample.data.allrecipes.AllRecipesRepository
import com.sample.data.recipedetail.RecipeDetailRepository
import com.sample.domain.allrecipes.AllRecipesUseCase
import com.sample.domain.mapper.AllRecipesDtoMapper
import com.sample.domain.mapper.RecipeDtoMapper
import com.sample.domain.recipedetail.RecipeDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    companion object{
        @Provides
        fun provideAllRecipeUseCase(
            allRecipesRepository: AllRecipesRepository,
            allRecipesDtoMapper: AllRecipesDtoMapper
        ): AllRecipesUseCase {
            return AllRecipesUseCase(
                allRecipesRepository,
                allRecipesDtoMapper
            )
        }

        @Provides
        fun provideRecipeDetailUseCase(
            recipeDetailRepository: RecipeDetailRepository,
            recipeDtoMapper: RecipeDtoMapper,
        ): RecipeDetailUseCase {
            return RecipeDetailUseCase(
                recipeDetailRepository,
                recipeDtoMapper
            )
        }
    }
}
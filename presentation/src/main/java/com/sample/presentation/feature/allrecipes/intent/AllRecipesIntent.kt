package com.sample.presentation.feature.allrecipes.intent

sealed class AllRecipesIntent {
    data object LoadPage : AllRecipesIntent()
}
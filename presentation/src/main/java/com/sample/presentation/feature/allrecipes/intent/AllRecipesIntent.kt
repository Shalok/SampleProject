package com.sample.presentation.feature.allrecipes.intent

sealed interface AllRecipesIntent {
    data object LoadPage : AllRecipesIntent
    data object RetryPageLoad : AllRecipesIntent
}
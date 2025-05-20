package com.sample.presentation.feature.allrecipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.core.networking.di.NetworkModule
import com.sample.core.networking.utility.Result.Error
import com.sample.core.networking.utility.Result.Success
import com.sample.domain.allrecipes.usecase.AllRecipesUseCase
import com.sample.presentation.feature.allrecipes.intent.AllRecipesIntent
import com.sample.presentation.feature.allrecipes.mapper.AllRecipesUiMapper
import com.sample.presentation.feature.allrecipes.uistate.AllRecipesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AllRecipeViewModel @Inject constructor(
    private val allRecipesUseCase: AllRecipesUseCase,
    private val allRecipesUiMapper: AllRecipesUiMapper
) : ViewModel() {

    private val _allRecipes = MutableStateFlow<AllRecipesUiState>(AllRecipesUiState.LOADING)
    val allRecipes: StateFlow<AllRecipesUiState> = _allRecipes

    fun handleEvent(event: AllRecipesIntent) {
        when (event) {
            AllRecipesIntent.LoadPage -> {
                viewModelScope.launch {
                    when (val res = allRecipesUseCase()) {
                        is Error -> _allRecipes.value = AllRecipesUiState.ErrorUiState(
                            errorMessage = res.throwable.message
                        )

                        is Success -> _allRecipes.update {
                            allRecipesUiMapper(res.data)
                        }
                    }
                }
            }

            AllRecipesIntent.RetryPageLoad -> _allRecipes.update {
                AllRecipesUiState.LOADING
            }
        }
    }
}
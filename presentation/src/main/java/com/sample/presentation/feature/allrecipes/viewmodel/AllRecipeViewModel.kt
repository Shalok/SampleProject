package com.sample.presentation.feature.allrecipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.core.networking.NetworkModule
import com.sample.domain.allrecipes.usecase.AllRecipesUseCase
import com.sample.domain.allrecipes.entities.AllRecipes
import com.sample.presentation.feature.allrecipes.intent.AllRecipesIntent
import com.sample.presentation.feature.allrecipes.mapper.AllRecipesUiMapper
import com.sample.presentation.feature.allrecipes.uistate.AllRecipesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.sample.core.networking.Result.Error
import com.sample.core.networking.Result.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@HiltViewModel
class AllRecipeViewModel @Inject constructor(
    private val allRecipesUseCase: AllRecipesUseCase,
    private val allRecipesUiMapper: AllRecipesUiMapper,
    @NetworkModule.IoDispatcher private val ioScheduler: CoroutineDispatcher
) : ViewModel() {

    private val _allRecipes = MutableStateFlow<AllRecipesUiState>(AllRecipesUiState.LOADING)
    val allRecipes: StateFlow<AllRecipesUiState> = _allRecipes

    fun handleEvent(event: AllRecipesIntent) {
        when (event) {
            AllRecipesIntent.LoadPage -> {
                viewModelScope.launch {
                    val res = withContext(ioScheduler) {
                        allRecipesUseCase()
                    }
                    when (res) {
                        is Error -> _allRecipes.value = AllRecipesUiState.ErrorUiState(
                            errorMessage = res.throwable?.message ?: "Something went wrong"
                        )

                        is Success<AllRecipes> -> _allRecipes.value =
                            allRecipesUiMapper.invoke(res.data)
                    }
                }
            }
        }
    }
}
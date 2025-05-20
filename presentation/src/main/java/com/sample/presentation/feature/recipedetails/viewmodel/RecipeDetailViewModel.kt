package com.sample.presentation.feature.recipedetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.core.networking.utility.Result
import com.sample.domain.recipedetail.entities.Recipe
import com.sample.domain.recipedetail.usecase.RecipeDetailUseCase
import com.sample.presentation.feature.recipedetails.intent.RecipeDetailIntent
import com.sample.presentation.feature.recipedetails.mapper.RecipeUiMapper
import com.sample.presentation.feature.recipedetails.uistate.RecipeDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipeDetailUseCase: RecipeDetailUseCase,
    private val stateHandle: SavedStateHandle,
    private val recipeUiMapper: RecipeUiMapper
) : ViewModel() {

    private val _recipeDetail = MutableStateFlow<RecipeDetailUiState>(RecipeDetailUiState.LOADING)
    val recipeDetail: StateFlow<RecipeDetailUiState> = _recipeDetail

    fun handleEvents(event: RecipeDetailIntent) {
        when (event) {
            RecipeDetailIntent.LoadPage -> {
                viewModelScope.launch {
                    val data = stateHandle.get<String>("recipeId")
                    data?.let {
                        when (val res = recipeDetailUseCase(data)) {
                            is Result.Error -> _recipeDetail.update {
                                RecipeDetailUiState.ErrorState(
                                    res.throwable.message
                                )
                            }

                            is Result.Success<Recipe> -> _recipeDetail.update {
                                recipeUiMapper(res.data)
                            }
                        }
                    }
                }
            }
        }
    }
}
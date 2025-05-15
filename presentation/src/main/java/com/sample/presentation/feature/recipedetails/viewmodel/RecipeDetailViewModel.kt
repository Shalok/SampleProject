package com.sample.presentation.feature.recipedetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.core.networking.NetworkModule
import com.sample.core.networking.Result
import com.sample.domain.recipedetail.entities.Recipe
import com.sample.domain.recipedetail.usecase.RecipeDetailUseCase
import com.sample.presentation.feature.recipedetails.intent.RecipeDetailIntent
import com.sample.presentation.feature.recipedetails.mapper.RecipeUiMapper
import com.sample.presentation.feature.recipedetails.uistate.RecipeDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipeDetailUseCase: RecipeDetailUseCase,
    private val stateHandle: SavedStateHandle,
    private val recipeUiMapper: RecipeUiMapper,
    @NetworkModule.IoDispatcher private val ioScheduler: CoroutineDispatcher
) : ViewModel() {

    private val _recipeDetail = MutableStateFlow<RecipeDetailUiState>(RecipeDetailUiState.LOADING)
    val recipeDetail: StateFlow<RecipeDetailUiState> = _recipeDetail

    fun handleEvents(event: RecipeDetailIntent) {
        when (event) {
            RecipeDetailIntent.LoadPage -> {
                viewModelScope.launch {
                    val data = stateHandle.get<String>("recipeId")
                    data?.let {
                        val res = withContext(ioScheduler) {
                            recipeDetailUseCase.invoke(data)
                        }
                        when (res) {
                            is Result.Error -> _recipeDetail.value =
                                RecipeDetailUiState.ErrorState(res.throwable?.message ?: "Unknown Error")

                            is Result.Success<Recipe> -> _recipeDetail.value =
                                recipeUiMapper.invoke(res.data)
                        }
                    }
                }
            }
        }
    }
}
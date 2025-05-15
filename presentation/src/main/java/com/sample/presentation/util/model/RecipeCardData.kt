package com.sample.presentation.util.model

import androidx.compose.ui.Modifier

data class RecipeCardData(
    val id: String,
    val name: String,
    val imageUrl: String,
    val cookingTime: String,
    val difficulty: String,
    val cuisine: String,
    val modifier: Modifier = Modifier,
    val callback: ((String) -> Unit)? = null
)

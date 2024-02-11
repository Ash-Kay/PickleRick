package io.ashkay.picklerick.ui

import io.ashkay.picklerick.models.Character

sealed class MainUiState {
    data object Loading : MainUiState()
    data class Success(val data: List<Character>) : MainUiState()
    data class Error(val throwable: Throwable): MainUiState()
}
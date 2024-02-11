package io.ashkay.picklerick.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ashkay.picklerick.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repository: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        _uiState.value = MainUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getCharacters(0)
                _uiState.value = MainUiState.Success(result.characters)
            } catch (e: Exception) {
                _uiState.value = MainUiState.Error(e)
            }
        }
    }

}
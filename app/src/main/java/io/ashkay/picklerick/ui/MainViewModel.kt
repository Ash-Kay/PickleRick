package io.ashkay.picklerick.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ashkay.picklerick.domain.Repository
import io.ashkay.picklerick.models.Character
import io.ashkay.picklerick.ui.MainUiState.Error
import io.ashkay.picklerick.ui.MainUiState.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState

    private val characterList = mutableListOf<Character>()

    private var page = 1

    init {
        loadCharacters(page)
    }

    fun loadMore() {
        page++
        loadCharacters(page)
    }

    private fun loadCharacters(page: Int) {
        _uiState.value = MainUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getCharacters(page)
                characterList.addAll(result.characters)
                _uiState.value = Success(characterList)
            } catch (e: Exception) {
                _uiState.value = Error(e)
            }
        }
    }
}
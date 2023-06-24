package com.example.gemficastore.ui.screen.games

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemficastore.data.GamesRepository
import com.example.gemficastore.model.Games
import com.example.gemficastore.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class GamesViewModel(private val gamesRepository : GamesRepository): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Games>>> = MutableStateFlow(UiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(query: String) = viewModelScope.launch {
        _query.value = query
        gamesRepository.searchGames(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect{
                _uiState.value = UiState.Success(it)
            }
    }

    fun updatesGames(gamesId: Long, newValue : Boolean) = viewModelScope.launch {
        gamesRepository.updateGame(gamesId, !newValue)
            .collect{isUpdated->
                if (isUpdated) search(_query.value)
            }
    }
}
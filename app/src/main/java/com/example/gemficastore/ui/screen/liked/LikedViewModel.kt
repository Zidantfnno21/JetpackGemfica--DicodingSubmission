package com.example.gemficastore.ui.screen.liked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemficastore.data.GamesRepository
import com.example.gemficastore.model.Games
import com.example.gemficastore.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LikedViewModel(private val gamesRepository : GamesRepository): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Games>>> = MutableStateFlow(UiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    fun getLikedGames() = viewModelScope.launch {
        gamesRepository.getFavoriteGame()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateGames(id: Long, newValue: Boolean) {
        gamesRepository.updateGame(id, !newValue)
        getLikedGames()
    }
}